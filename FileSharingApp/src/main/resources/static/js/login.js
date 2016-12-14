var app = angular.module("login",[])
.controller("loginCtrl",function($scope){
	$scope.scriptError = false;
	$scope.submitForm = function($scope){
		if($scope.userName==null){
			$scope.scriptError = true;
			$scope.error = "User Details cannot be empty";
		}
	}
});