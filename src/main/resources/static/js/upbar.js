// 서버로부터 사용자 이름 가져오기
function fetchUserName() {

    body = JSON.stringify({
        title: "getuser"

    })
    function success(username){
        displayUserInfo(username);
    }
    function fail(){
        displayLoginButton();
    }
    httpRequest('GET',`/users/email`, body, success, fail);
}

function displayUserInfo(username) {
    const greetingElement = document.getElementById('username');
    if (greetingElement) {
        greetingElement.textContent = username;
    }
    const userDropdown = document.getElementById('userDropdown');
    const loginNavItem = document.getElementById('loginNavItem');
    if (userDropdown && loginNavItem) {
        userDropdown.style.display = 'block';
        loginNavItem.style.display = 'none';
    }
}

function displayLoginButton() {
    const userDropdown = document.getElementById('userDropdown');
    const loginNavItem = document.getElementById('loginNavItem');

    if (userDropdown && loginNavItem) {
        userDropdown.style.display = 'none';
        loginNavItem.style.display = 'block';
    }
}


// 프로필 URL을 쿠키에서 읽어오는 함수
function getProfilePictureUrlFromCookie() {
    const cookies = document.cookie.split(';'); // 모든 쿠키 가져오기
    let profilePictureUrl = null;
    for (const cookie of cookies) {
        const cookiePair = cookie.split('=');
        const cookieName = cookiePair[0].trim();
        if (cookieName === 'profilePictureUrl') {
            profilePictureUrl = cookiePair[1]; // 프로필 URL 값 가져오기
            break;
        }
    }

    return profilePictureUrl;
}




// DOM 로딩 완료 시 프로필 URL 읽어와서 이미지 엘리먼트에 설정
function httpRequest(method, url, body, success, fail) {
    alert(getCookie('access_token'));
    fetch(url, {
        method: method,
        headers: { // 로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
            'Content-Type': 'application/json',
        },
    }).then(response => {
        if (response.status === 200 || response.status === 201) {
            return response.text();
        }
        const refresh_token = getCookie('refresh_token');
        if (response.status === 401 && refresh_token) {
            fetch('/api/token', {
                method: 'POST',
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    refreshToken: getCookie('refresh_token'),
                }),
            })
                .then(res => {
                    if (res.ok) {
                        return res.json();
                    }
                })
                .then(result => { // 재발급이 성공하면 로컬 스토리지값을 새로운 액세스 토큰으로 교체
                    localStorage.setItem('access_token', result.accessToken);
                    httpRequest(method, url, body, success, fail);
                })
                .catch(
                    error => fail());
        } else {
            return fail();
        }
    })
        .then(data => {
            if (data === "") {
                httpRequest('GET', `/api/getname`, body, success, fail);
            } else {
                return success(data); // 파싱된 데이터를 성공 콜백에 전달
            }
        });
}

// 쿠키를 가져오는 함수
function getCookie(key) {
    var result = null;
    var cookie = document.cookie.split(';');
    cookie.some(function (item) {
        item = item.replace(' ', '');

        var dic = item.split('=');

        if (key === dic[0]) {
            result = dic[1];
            return true;
        }
    });

    return result;
}

document.addEventListener('DOMContentLoaded', function () {
    fetchUserName();
    const profilePictureUrl = getProfilePictureUrlFromCookie();
    if (profilePictureUrl) {
        const userAvatarImg = document.getElementById('userAvatar');
        userAvatarImg.src = profilePictureUrl;
    }
});