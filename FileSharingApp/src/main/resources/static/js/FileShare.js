var fileshareApp = angular.module("fileShareApp", [ "ngRoute" ]).config(
		function($routeProvider,$locationProvider) {
			$routeProvider.when("/home", {
				templateUrl : "home.html",
				controller : "homeController"
			}).when("/login", {
				templateUrl : "login.html",
				controller : "loginController"
			}).when("/register", {
				templateUrl : "register.html",
				controller : "registerController"
			}).otherwise({
				redirectTo : "/home"
			})
			$locationProvider.html5Mode(true);
		}).controller("homeController", function($scope) {
	$scope.message = "File Sharing Application";

}).controller("loginController", function($scope,$http,$location) {
	$scope.loginPage = "Login Page";
	$scope.scriptError = false;
	$scope.authenticated = false;
	$scope.submitForm = function(){
		if($scope.userName==undefined || $scope.password == undefined){
			$scope.scriptError = true;
			$scope.error = "User Details cannot be empty";
		}else{
			var headers = $scope ? {authorization : "Basic "
		        + btoa($scope.userName + ":" + $scope.userName)
		    } : {};
			$http({
				method:'GET',
				url:'/authenticate',
				headers : headers
			}).then(function (response){
				var details = response.data;
				console.log(response.data);
				if(details!=null){
					$scope.authenticated = true;
					$scope.template="upload.html";
				}
			},function(response){
					if (response.status === 0) {
						$scope.error = 'No connection. Verify application is running.';
					} else if (response.status == 401) {
						$scope.error = 'Unauthorized.';
					} else if (response.status == 403) {
						$scope.error = 'Forbidden.';
					} else {
						$scope.error = 'Unknown.';			
					}
					$scope.scriptError = true;
					$scope.authenticated = true;
					$scope.template="unauthenticated.html";
			})
		}
	}

}).controller("registerController", function($scope) {

})