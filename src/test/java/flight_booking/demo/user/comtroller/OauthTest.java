package flight_booking.demo.user.comtroller;

import flight_booking.demo.BaseTest;
import flight_booking.demo.domain.user.entity.MemberShip;
import flight_booking.demo.domain.user.entity.Role;
import flight_booking.demo.domain.user.entity.User;
import flight_booking.demo.security.utils.UserUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class OauthTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginPageRedirectsToGoogleOAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/oauth2/authorization/google"))
                .andExpect(MockMvcResultMatchers.status().isFound()) // `302 Found` 검증
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("https://accounts.google.com/**")); // 리디렉트 URL 패턴 확인
    }

    @Test
    public void testOAuthRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/oauth2/authorization/google"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.containsString("accounts.google.com/o/oauth2")));
    }
    @Test
    @WithMockUser
    public void testOAuthLoginReturnsUserInfo() throws Exception {
        //  테스트용 사용자 생성
        User testUser = new User();
        testUser.setId("123");
        testUser.setEmail("johndoe@example.com");
        testUser.setName("John Doe");
        testUser.setProfile_url("https://example.com/johndoe.jpg");
        testUser.setRole(Role.CUSTOMER);
        testUser.setMembership(MemberShip.BASIC);

        // SecurityContext에 User 설정
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                testUser, null, List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // MockMvc를 사용해 GET 요청 실행
        mockMvc.perform(MockMvcRequestBuilders.get("/users/me")
                        .with(SecurityMockMvcRequestPostProcessors.user(testUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("johndoe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));
    }

    @Test
    @WithMockUser
    public void testLogoutRedirectsToHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/logout"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }
}
