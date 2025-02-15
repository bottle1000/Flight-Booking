document.addEventListener('DOMContentLoaded', function() {
    var dropdownElement = document.getElementById('navbarDropdownMenuLink');
    var dropdown = new bootstrap.Dropdown(dropdownElement);
    var settingLink = document.getElementById('settingLink');
    var recordLink = document.getElementById('recordLink');
    var settingPanel = document.getElementById('sidebar-user-setting');
    var recordPanel = document.getElementById('sidebar-user-record');
    var editSubNicknameLink = document.getElementById('editsubnickname');
    var myProfileLink = document.getElementById('myProfileLink');

    if (editSubNicknameLink) {
        editSubNicknameLink.addEventListener('click', function(event) {
            event.preventDefault(); // 기본 동작 방지 (페이지 이동 취소)

            var nicknameInput = document.getElementById('nicknameInput');
            var subnicknameValue = nicknameInput.value.trim(); // 공백 제거

            if (subnicknameValue === "") {
                alert("닉네임을 입력하세요.");
            } else {
                var body = JSON.stringify({
                    subnickname: subnicknameValue
                });

                function subfunction(data) {
                         if (data === "") {
                                try {

                                                  httpRequest1('PUT', `/api/updatesubnickname`, body, null);
                                                  const greetingElement = document.getElementById('username');
                                                  greetingElement.textContent = subnicknameValue;
                                } catch (error) {
                                    console.error('Success function error:', error);
                                }
                            } else {
                                try {
                                  alert("이미 닉네임이 존재합니다.")
                                } catch (error) {
                                    console.error('Fail function error:', error);
                                }
                            }
                }

                httpRequest1('POST', `/api/getsubnamebysubname`, body, subfunction);
            }
        });
    }

    settingLink.addEventListener('click', function(event) {
        event.preventDefault();
        togglePanel(settingPanel);
    });

    recordLink.addEventListener('click', function(event) {
        event.preventDefault();
        togglePanel(recordPanel);

                        event.preventDefault(); // 기본 동작 방지 (페이지 이동 취소)
               var sidebaruserrecord = document.getElementById('sidebar-user-record');

                             function subfunction(data) {
                                    sidebaruserrecord.textContent = data;
                             }
                            httpRequest1('GET', `/api/getrecord`, null, subfunction);
    });

    myProfileLink.addEventListener('click', function(event) {
        event.preventDefault();
        toggleSidebar();
    });

    document.addEventListener('click', function(event) {
        var sidebar = document.getElementById('customSidebar');
        var upbar = document.getElementById('upbar');

        if (!sidebar.contains(event.target) && event.target !== myProfileLink && !upbar.contains(event.target)) {
            if (sidebar.classList.contains('show')) {
                toggleSidebar();
            }
        }
    });

    function toggleSidebar() {
        var sidebar = document.getElementById('customSidebar');
        var upbar = document.getElementById('upbar');

        if (sidebar.classList.contains('show')) {
            if (upbar) {
                upbar.style.transform = 'translate(0px)';
            }
            sidebar.classList.remove('show');
        } else {
            if (upbar) {
                upbar.style.transform = 'translate(-245px)';
            }
            sidebar.classList.add('show');
        }
    }

    function togglePanel(panel) {
        var allPanels = document.querySelectorAll('.sidebar-panel');
        allPanels.forEach(function(p) {
            if (p !== panel && p.classList.contains('show')) {
                p.classList.remove('show');
            }
        });

        if (panel.classList.contains('show')) {
            panel.classList.remove('show');
        } else {
            panel.classList.add('show');
        }
    }
});

function httpRequest1(method, url, body, subfunction) {
    fetch(url, {
        method: method,
        headers: {
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
            'Content-Type': 'application/json',
        },
        body: body,
    }).then(response => {
        if (response.status === 200 || response.status === 201) {
            return response.text();
        }
        const refresh_token = getCookie1('refresh_token');
        if (response.status === 401 && refresh_token) {
            fetch('/api/token', {
                method: 'POST',
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    refreshToken: getCookie1('refresh_token'),
                }),
            }).then(res => {
                if (res.ok) {
                    return res.json();
                }
            }).then(result => {
                localStorage.setItem('access_token', result.accessToken);
                httpRequest1(method, url, body, subfunction);
            }).catch(error => fail());
        } else {
            return fail();
        }
    }).then(data => {
        subfunction(data)
    });
}

function getCookie1(key) {
    var result = null;
    var cookie = document.cookie.split(';');
    cookie.some(function(item) {
        item = item.replace(' ', '');
        var dic = item.split('=');
        if (key === dic[0]) {
            result = dic[1];
            return true;
        }
    });
    return result;
}
