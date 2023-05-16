package br.com.diego.msrelatorio.core.business;

import br.com.diego.msrelatorio.adapters.out.providers.AuthenticationProvider;
import br.com.diego.msrelatorio.config.security.JwtService;
import br.com.diego.msrelatorio.config.security.entity.Role;
import br.com.diego.msrelatorio.core.domain.Token;
import br.com.diego.msrelatorio.core.domain.User;
import br.com.diego.msrelatorio.openapi.dto.AuthenticationRequest;
import br.com.diego.msrelatorio.openapi.dto.AuthenticationResponse;
import br.com.diego.msrelatorio.openapi.dto.RegisterRequest;
import br.com.diego.msrelatorio.ports.AuthenticationProviderPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AuthenticationBusinessTest {

    private AuthenticationProviderPort authenticationProviderPort = Mockito.mock(AuthenticationProvider.class);
    private PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
    private JwtService jwtService = Mockito.mock(JwtService.class);
    private AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);

    private AuthenticationBusiness authenticationBusiness = new AuthenticationBusiness(authenticationProviderPort,
            passwordEncoder,
            jwtService,
            authenticationManager);

    @Test
    void registerSuccess(){
        var request = RegisterRequest.builder()
                .firstname("Fulano")
                .lastname("de Tal")
                .email("teste@teste.com")
                .password("123456")
                .role(RegisterRequest.RoleEnum.ADMIN)
                .build();

        var response = AuthenticationResponse.builder()
                .accessToken("123")
                .refreshToken("456")
                .build();

        User user = User.builder()
                .id("123")
                .firstname("Fulano")
                .lastname("de Tal")
                .email("teste@teste.com")
                .password("123456")
                .role(Role.ADMIN.name())
                .build();

        Token token = Token.builder()
                .token("123")
                .expired(false)
                .revoked(false)
                .userName("Fulano")
                .id("456")
                .build();

        Mockito.when(passwordEncoder.encode(any())).thenReturn("pwd1234");
        Mockito.when(authenticationProviderPort.saveUser(any())).thenReturn(user);
        Mockito.when(jwtService.generateToken(any(User.class))).thenReturn("123");
        Mockito.when(jwtService.generateRefreshToken(any(User.class))).thenReturn("456");
        Mockito.when(authenticationProviderPort.saveToken(any(Token.class))).thenReturn(token);

        var result = authenticationBusiness.register(request);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getAccessToken(), response.getAccessToken());
        Assertions.assertEquals(result.getRefreshToken(), response.getRefreshToken());

    }

    @Test
    void authenticateSuccess(){
        var request = AuthenticationRequest.builder()
                .email("teste@teste.com")
                .password("123456")
                .build();

        var response = AuthenticationResponse.builder()
                .accessToken("123")
                .refreshToken("456")
                .build();

        User user = User.builder()
                .id("123")
                .firstname("Fulano")
                .lastname("de Tal")
                .email("teste@teste.com")
                .password("123456")
                .role(Role.ADMIN.name())
                .build();

        Token token = Token.builder()
                .token("123")
                .expired(false)
                .revoked(false)
                .userName("Fulano")
                .id("456")
                .build();

        Mockito.when(authenticationProviderPort.findByEmail(any())).thenReturn(user);
        Mockito.when(jwtService.generateToken(any(User.class))).thenReturn("123");
        Mockito.when(jwtService.generateRefreshToken(any(User.class))).thenReturn("456");
        Mockito.when(authenticationProviderPort.findTokenByUser(any())).thenReturn(List.of(token));
        Mockito.when(authenticationProviderPort.saveToken(any(Token.class))).thenReturn(token);
        Mockito.when(authenticationProviderPort.saveTokens(any())).thenReturn(List.of(token));

        var result = authenticationBusiness.authenticate(request);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getAccessToken(), response.getAccessToken());
        Assertions.assertEquals(result.getRefreshToken(), response.getRefreshToken());

    }

    @Test
    void refreshTokenSuccess() throws IOException {

        User user = User.builder()
                .id("123")
                .firstname("Fulano")
                .lastname("de Tal")
                .email("123456@teste.com")
                .password("123456")
                .role(Role.ADMIN.name())
                .build();

        Token token = Token.builder()
                .token("123")
                .expired(false)
                .revoked(false)
                .userName("Fulano")
                .id("456")
                .build();

        var httpRequest = this.httpServletRequestBuilder();
        var httpResponse = this.httpServletResponseBuilder();

        Mockito.when(authenticationProviderPort.findByEmail(any())).thenReturn(user);
        Mockito.when(jwtService.extractUsername(any())).thenReturn("123456@teste.com");
        Mockito.when(jwtService.generateToken(any(User.class))).thenReturn("6543210");
        Mockito.when(jwtService.generateRefreshToken(any(User.class))).thenReturn("6543210");
        Mockito.when(jwtService.isTokenValid(any(), any(User.class))).thenReturn(true);
        Mockito.when(authenticationProviderPort.findTokenByUser(any())).thenReturn(List.of(token));
        Mockito.when(authenticationProviderPort.saveToken(any(Token.class))).thenReturn(token);
        Mockito.when(authenticationProviderPort.saveTokens(any())).thenReturn(List.of(token));

        authenticationBusiness.refreshToken(httpRequest, httpResponse);

    }

    private HttpServletRequest httpServletRequestBuilder() {
        return new HttpServletRequest() {
            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getAttributeNames() {
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

            }

            @Override
            public int getContentLength() {
                return 0;
            }

            @Override
            public long getContentLengthLong() {
                return 0;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletInputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public String getParameter(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getParameterNames() {
                return null;
            }

            @Override
            public String[] getParameterValues(String s) {
                return new String[0];
            }

            @Override
            public Map<String, String[]> getParameterMap() {
                return null;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public String getScheme() {
                return null;
            }

            @Override
            public String getServerName() {
                return null;
            }

            @Override
            public int getServerPort() {
                return 0;
            }

            @Override
            public BufferedReader getReader() throws IOException {
                return null;
            }

            @Override
            public String getRemoteAddr() {
                return null;
            }

            @Override
            public String getRemoteHost() {
                return null;
            }

            @Override
            public void setAttribute(String s, Object o) {

            }

            @Override
            public void removeAttribute(String s) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public Enumeration<Locale> getLocales() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public RequestDispatcher getRequestDispatcher(String s) {
                return null;
            }

            @Override
            public String getRealPath(String s) {
                return null;
            }

            @Override
            public int getRemotePort() {
                return 0;
            }

            @Override
            public String getLocalName() {
                return null;
            }

            @Override
            public String getLocalAddr() {
                return null;
            }

            @Override
            public int getLocalPort() {
                return 0;
            }

            @Override
            public ServletContext getServletContext() {
                return null;
            }

            @Override
            public AsyncContext startAsync() throws IllegalStateException {
                return null;
            }

            @Override
            public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
                return null;
            }

            @Override
            public boolean isAsyncStarted() {
                return false;
            }

            @Override
            public boolean isAsyncSupported() {
                return false;
            }

            @Override
            public AsyncContext getAsyncContext() {
                return null;
            }

            @Override
            public DispatcherType getDispatcherType() {
                return null;
            }

            @Override
            public String getAuthType() {
                return null;
            }

            @Override
            public Cookie[] getCookies() {
                return new Cookie[0];
            }

            @Override
            public long getDateHeader(String s) {
                return 0;
            }

            @Override
            public String getHeader(String s) {
                return "Bearer 123sdfsdfwe4232rewfw32432rewfd";
            }

            @Override
            public Enumeration<String> getHeaders(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                return null;
            }

            @Override
            public int getIntHeader(String s) {
                return 0;
            }

            @Override
            public String getMethod() {
                return null;
            }

            @Override
            public String getPathInfo() {
                return null;
            }

            @Override
            public String getPathTranslated() {
                return null;
            }

            @Override
            public String getContextPath() {
                return null;
            }

            @Override
            public String getQueryString() {
                return null;
            }

            @Override
            public String getRemoteUser() {
                return null;
            }

            @Override
            public boolean isUserInRole(String s) {
                return false;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public String getRequestedSessionId() {
                return null;
            }

            @Override
            public String getRequestURI() {
                return null;
            }

            @Override
            public StringBuffer getRequestURL() {
                return null;
            }

            @Override
            public String getServletPath() {
                return null;
            }

            @Override
            public HttpSession getSession(boolean b) {
                return null;
            }

            @Override
            public HttpSession getSession() {
                return null;
            }

            @Override
            public String changeSessionId() {
                return null;
            }

            @Override
            public boolean isRequestedSessionIdValid() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromCookie() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromURL() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromUrl() {
                return false;
            }

            @Override
            public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
                return false;
            }

            @Override
            public void login(String s, String s1) throws ServletException {

            }

            @Override
            public void logout() throws ServletException {

            }

            @Override
            public Collection<Part> getParts() throws IOException, ServletException {
                return null;
            }

            @Override
            public Part getPart(String s) throws IOException, ServletException {
                return null;
            }

            @Override
            public <T extends HttpUpgradeHandler> T upgrade(Class<T> aClass) throws IOException, ServletException {
                return null;
            }
        };
    }

    private HttpServletResponse httpServletResponseBuilder() {
        return new HttpServletResponse() {
            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletOutputStream getOutputStream() throws IOException {
                return new ServletOutputStream() {
                    @Override
                    public boolean isReady() {
                        return false;
                    }

                    @Override
                    public void setWriteListener(WriteListener writeListener) {

                    }

                    @Override
                    public void write(int b) throws IOException {

                    }
                };
            }

            @Override
            public PrintWriter getWriter() throws IOException {
                return null;
            }

            @Override
            public void setCharacterEncoding(String s) {

            }

            @Override
            public void setContentLength(int i) {

            }

            @Override
            public void setContentLengthLong(long l) {

            }

            @Override
            public void setContentType(String s) {

            }

            @Override
            public void setBufferSize(int i) {

            }

            @Override
            public int getBufferSize() {
                return 0;
            }

            @Override
            public void flushBuffer() throws IOException {

            }

            @Override
            public void resetBuffer() {

            }

            @Override
            public boolean isCommitted() {
                return false;
            }

            @Override
            public void reset() {

            }

            @Override
            public void setLocale(Locale locale) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public void addCookie(Cookie cookie) {

            }

            @Override
            public boolean containsHeader(String s) {
                return false;
            }

            @Override
            public String encodeURL(String s) {
                return null;
            }

            @Override
            public String encodeRedirectURL(String s) {
                return null;
            }

            @Override
            public String encodeUrl(String s) {
                return null;
            }

            @Override
            public String encodeRedirectUrl(String s) {
                return null;
            }

            @Override
            public void sendError(int i, String s) throws IOException {

            }

            @Override
            public void sendError(int i) throws IOException {

            }

            @Override
            public void sendRedirect(String s) throws IOException {

            }

            @Override
            public void setDateHeader(String s, long l) {

            }

            @Override
            public void addDateHeader(String s, long l) {

            }

            @Override
            public void setHeader(String s, String s1) {

            }

            @Override
            public void addHeader(String s, String s1) {

            }

            @Override
            public void setIntHeader(String s, int i) {

            }

            @Override
            public void addIntHeader(String s, int i) {

            }

            @Override
            public void setStatus(int i) {

            }

            @Override
            public void setStatus(int i, String s) {

            }

            @Override
            public int getStatus() {
                return 0;
            }

            @Override
            public String getHeader(String s) {
                return null;
            }

            @Override
            public Collection<String> getHeaders(String s) {
                return null;
            }

            @Override
            public Collection<String> getHeaderNames() {
                return null;
            }
        };
    }
}
