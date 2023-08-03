<%@page import="java.net.URLEncoder"%>
<%@page import="java.security.SecureRandom"%>
<%@page import="java.math.BigInteger"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<script src='/resources/js/jquery-3.7.0.js'></script>

<script src="/resources/js/login.js"></script>

<title>페코 로그인</title>


<!-- Bootstrap core CSS -->
<%@include file="./commo/css.jsp"%>

</head>

<body>

	<!-- ***** Preloader Start ***** -->
	<div id="js-preloader" class="js-preloader">
		<div class="preloader-inner">
			<span class="dot"></span>
			<div class="dots">
				<span></span> <span></span> <span></span>
			</div>
		</div>
	</div>
	<!-- ***** Preloader End ***** -->

	<!-- header -->
	<%@include file="./commo/header.jsp"%>

	<!-- 로그인 -->
	<div class="container">
		<div class="page-content">
			<!-- ***** login Start *****-->

			<form name="signinForm" id="signinForm"
				<c:if test="${not empty showSignupForm}"> style='display: none;' </c:if>>
				<div class="login-img">
					<!-- 이미지 -->
				</div>
				<div id="msg"></div>
				<h1 class="h3 mb-3 fw-normal">Please sign in</h1>

				<div class="form-floating">
					<input type="text" class="form-control" id="id"> <label
						for="id">id</label>
				</div>
				<div class="form-floating">
					<input type="password" class="form-control" id="pw"> <label
						for="pw">Password</label>
				</div>

				<button class="w-100 btn btn-lg btn-warning" type="submit"
					id="btnLogin">Login</button>

				<a class="mt-5 mb-3 text-muted" id='btnSignupView'>회원가입</a>
				<!-- 네이버 로그인 -->
				<%
					String clientId = "vThUa8bHb7IlAn52GUi5";//애플리케이션 클라이언트 아이디값";
					String redirectURI = URLEncoder.encode("http://localhost:8080/peco/naver_callback", "UTF-8");
					SecureRandom random = new SecureRandom();
					String state = new BigInteger(130, random).toString();

					// 요청URL -> 네이버 로그인및 사용자정보제공 동의 -> 콜백으로 코드를 제공
					String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
					apiURL += "&client_id=" + clientId;
					apiURL += "&redirect_uri=" + redirectURI;
					apiURL += "&state=" + state;
					session.setAttribute("state", state);
				%>
				
				
				<a href="<%=apiURL%>"><img height="80"
					src="/resources/img/naver.png" /></a>
				<!-- 네이버 로그인 end-->

				<!-- 카카오 로그인 -->

					<img alt="카카오로그인"
						src="/resources/img/certi_kakao_login.png"
						onclick="loginWithKakao()">

				<script type="text/javascript"
					src="https://developers.kakao.com/sdk/js/kakao.min.js"
					charset="utf-8"></script>
				<script type="text/javascript">
					$(document).ready(function() {
						Kakao.init('5b1adb5037f620021df4924bf30ee375');
						Kakao.isInitialized();
					});

					function loginWithKakao() {
						Kakao.Auth
								.authorize({
									redirectUri : 'http://localhost:8080/peco/kakao_callback'
								}); // 등록한 리다이렉트uri 입력
					}
				</script>
				<!-- 카카오 로그인 End -->
			</form>
			<!-- ***** login End ***** -->

			<!-- ***** 회원 가입 ***** -->
			<form name='signupForm' id='signupForm'
				<c:if test="${empty showSignupForm}"> style='display: none;' </c:if>>

				<div class="login-img">
					<!-- 이미지 -->
				</div>


				<!-- 회원 가입 체크 인풋 -->
				<c:if test="${empty showSignupForm}">
					<input type="hidden" id="idCheckRes">
				</c:if>
				<c:if test="${not empty showSignupForm}">
					<input type="hidden" id="idCheckRes" value="1">
				</c:if>

				<c:if test="${empty showSignupForm}">
					<input type="hidden" id="emailCheckRes">
				</c:if>
				<c:if test="${not empty showSignupForm}">
					<input type="hidden" id="emailCheckRes" value="1">
				</c:if>

				<c:if test="${empty showSignupForm}">
					<input type="hidden" id="pwCheckRes">
				</c:if>
				<c:if test="${not empty showSignupForm}">
					<input type="hidden" id="pwCheckRes" value="1">
				</c:if>

				<input type="hidden" id="nickNameRes"> <input type="hidden"
					name="m_id" id="m_id" value="">
				<h1 class="h3 mb-3 fw-normal">Please sign up</h1>
				<div id="signupMsg"></div>
				<div class="form-floating">
					<input type="text" class="form-control" id="signUpName"
						value="${apiName }"> <label for="signUpName">name</label>
				</div>

				<c:if test="${empty showSignupForm}">
					<div class="form-floating">
						<input type="text" class="form-control" id="signUpId"
							value="${apiId }"> <label for="signUpid">Id</label>
						<button class="btn btn-lg btn-warning" type="button" id="btnid">아이디
							중복 검사</button>
					</div>
				</c:if>

				<c:if test="${not empty showSignupForm}">
					<div class="form-floating">
						<input type="hidden" class="form-control" id="signUpId"
							value="${apiId }">
						<button class="btn btn-lg btn-warning" type="hidden" id="btnid"
							style="display: none;">아이디 중복 검사</button>
					</div>
				</c:if>

				<c:if test="${empty showSignupForm}">
					<div class="form-floating">
						<input type="password" class="form-control" id="signUpPw">
						<label for="signUpPw">Password</label>
					</div>

					<div class="form-floating">
						<input type="password" class="form-control" id="pwCheck">
						<label for="signUpPwCheck">Password Check</label>
					</div>
				</c:if>

				<c:if test="${not empty showSignupForm}">
					<div class="form-floating">
						<input type="hidden" class="form-control" id="signUpPw"
							value="null"> <label for="signUpPw">Password</label>
					</div>

					<div class="form-floating">
						<input type="hidden" class="form-control" id="pwCheck"
							value="null"> <label for="signUpPwCheck">Password
							Check</label>
					</div>
				</c:if>

				<div class="form-floating">
					<input type="text" class="form-control" id="nickName"> <label
						for="signUpPwCheck">NickName</label>
					<button class="btn btn-lg btn-warning" required="required"
						type="button" id="btnNickName">닉네임 중복 검사</button>
				</div>

				<div class="form-floating">
					<input type="text" class="form-control" id="age"> <label
						for="signUpPwCheck">Age</label>
				</div>

				<c:if test="${empty showSignupForm}">
					<div class="form-floating">
						<%@include file="./emailCheck.jsp"%>
						<input type="hidden" id="email">
					</div>
				</c:if>

				<c:if test="${not empty showSignupForm}">
					<div class="form-floating">
						<input type="" hidden"" id="email" value=${apiEmail }>
					</div>
				</c:if>

				<div class="form-floating">
					<input type="text" class="form-control" id="phone"
						value="${apiMobile }"> <label for="signUpPwCheck">Phone
						number</label>
				</div>

				<div class="form-floating">
					<input type="file" class="form-control" id="files" name="files">
					<label for="signUpPwCheck">프로필첨부</label>
				</div>


				<button class="w-100 btn btn-lg btn-primary" type="submit"
					id="btnSignup">회원가입</button>
				<a class="mt-5 mb-3 text-muted" id='btnSigninView'>로그인</a>
			</form>
			<!-- ***** 회원가입 End ***** -->
		</div>
	</div>


	<footer> </footer>


	<!-- Scripts -->
	<!-- Bootstrap core JavaScript -->
	<script src="/resources/vendor/jquery/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.2.0/js/bootstrap.min.js"
		integrity="sha512-8Y8eGK92dzouwpROIppwr+0kPauu0qqtnzZZNEF8Pat5tuRNJxJXCkbQfJ0HlUG3y1HB3z18CSKmUo7i2zcPpg=="
		crossorigin="anonymous" referrerpolicy="no-referrer"></script>

	<script src="/resources/assets/js/isotope.min.js"></script>
	<script src="/resources/assets/js/owl-carousel.js"></script>
	<script src="/resources/assets/js/tabs.js"></script>
	<script src="/resources/assets/js/popup.js"></script>
	<script src="/resources/assets/js/custom.js"></script>


</body>

</html>