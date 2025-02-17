package flight_booking.demo.security.utils;

import flight_booking.demo.domain.user.dto.request.UpdateMemberShipRequestDto;
import flight_booking.demo.domain.user.dto.request.UpdateRoleRequestDto;
import flight_booking.demo.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.util.List;

public class SecurityHolderUtil {
    public static void UpdateUser(User updatedUser, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            System.out.println("🚨 SecurityContext가 비어 있음");
            return;
        }

        // ✅ 새로운 Authentication 객체 생성
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedUser,
                authentication.getCredentials(),
                List.of(new SimpleGrantedAuthority("ROLE_" + updatedUser.getRole().name()))
        );

        // ✅ SecurityContext 업데이트
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        // ✅ SecurityContext를 요청 간 유지하도록 저장
        new HttpSessionSecurityContextRepository().saveContext(SecurityContextHolder.getContext(), request, response);

        // 🔥 SecurityContext가 즉시 반영되었는지 확인
        Authentication updatedAuth = SecurityContextHolder.getContext().getAuthentication();
        if (updatedAuth != null && updatedAuth.getPrincipal() instanceof User) {
            User updatedUserCheck = (User) updatedAuth.getPrincipal();
            System.out.println("🔍 SecurityContext 즉시 반영됨! 새로운 Role: " + updatedUserCheck.getRole());
        } else {
            System.out.println("🚨 SecurityContext 업데이트 후 확인 실패!");
        }

        System.out.println("✅ SecurityContext 업데이트 완료! 새로운 Role: " + updatedUser.getRole());
    }
}