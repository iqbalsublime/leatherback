leatherback.controller('mainCtrl', ['$scope','$location', 'prescriptionService', function($scope, $location, prescriptionService) {
	$scope.maxSize = 5;
	$scope.currentPage = 1;
	
    $scope.prescriptions = [];
    $scope.$watch('currentPage', function() {
    	prescriptionService.list($scope.currentPage).then(function(returnData) {
            $scope.prescriptions = returnData.data;
            $scope.setPageStatus(returnData.totalItems, returnData.currentPage);
        });
    });
    
    $scope.setPageStatus = function(totalItems, currentPage) {
        $scope.totalItems = totalItems;
        $scope.currentPage = currentPage;
    };
}]);


leatherback.controller('search.byLotNumberCtrl', ['$scope','$location', function($scope, $location) {
	$scope.message = "Hello AngularJs";

}]);


leatherback.controller('search.byDateCtrl', ['$scope','$location', function($scope, $location) {
	$scope.message = "Hello AngularJs";

}]);


leatherback.controller('search.byPartNumberCtrl', ['$scope','$location', function($scope, $location) {
	$scope.message = "Hello AngularJs";

}]);


leatherback.controller('addCtrl', ['$scope','$location', '$filter', function($scope, $location, $filter) {
	$scope.partNumberHeads = [{'partNumberHead':'TS'},
	                          {"partNumberHead":"TC"},
	                          {"partNumberHead":"TA"},
	                          {"partNumberHead":"TZ"},
	                          {"partNumberHead":"TSO"}];
	
	$scope.open = function($event) {
	    $event.preventDefault();
	    $event.stopPropagation();
	    $scope.opened = true;
	};

	$scope.dateOptions = {
		    formatYear: 'yy',
		    startingDay: 0
	};
	
	$scope.prescription = {};
	$scope.prescription.date = $filter('date')(new Date(), 'yyyy-MM-dd');
	
	$scope.$watch('prescription.date', function() {
		$scope.lotNumberHead = $filter('date')($scope.prescription.date, 'yyyyMMdd');
	})
	
	$scope.names = [];
	$scope.amount = [];
	$scope.price = [];
	$scope.amount = [];
	
	$scope.submit = function() {
		alert($scope.names[0]);
		alert($scope.names[1]);
		alert($scope.names[2]);
	};
	
}]);