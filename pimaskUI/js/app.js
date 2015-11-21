var app = angular.module('PiMask',['ngRoute'])
.config(function($routeProvider){
	$routeProvider.when('/connectedDevices', {
		templateUrl: '/templates/connected-devices.html',
		controller: 'ShowDevicesController',
		controllerAs:'showDevicesCtrl'
	})
	.when('/findDevices', {
		templateUrl: '/templates/find-devices.html',
		controller: 'FindDevicesController',
		controllerAs:'findDevicesCtrl'
	})
	.when('/', {
		redirectTo:'/connectedDevices'
	})
	.when('/configureDevices', {
		templateUrl: 'templates/configure-devices.html',
		controller: 'ConfigureDevicesController',
		controllerAs: 'configureDevicesCtrl'
	})
	.when('/editDevices', {
		templateUrl: 'templates/edit-devices.html',
		controller: 'EditDevicesController',
		controllerAs: 'editDevicesCtrl'
	})
});

app.controller('FindDevicesController', function($scope, $http){
	// $http.get('http://localhost:8080/pimask/find_devices')
	// .success(function(response){
	 	var response = [
	 					{"ip": "130.65.100.1", "hostname": "NewPi1"},
	 					{"ip": "130.65.100.2", "hostname": "NewPi2"},
	 					{"ip": "130.65.100.3", "hostname": "newPi3"}
	 							];
	 	$scope.names = response;
	// })
	// .error(function(){
	 //	alert("error");
	// });
});

app.controller('ShowDevicesController', function($scope, $http){
	var response = [{"hostname": "Pi1", "ip": "130.65.200.1"},
					{"hostname": "Pi2", "ip": "130.65.200.2"},
					{"hostname": "Pi3", "ip": "130.65.200.3"}];
	$scope.names = response;
});

app.controller('ConfigureDevicesController', function($scope, $http){
	var response = {};
	$scope.names = response;
});

app.controller('EditDevicesController', function($scope, $http){
	var response = {};
	$scope.names = response;
});