leatherback.factory('partNumberFactory', function() {
	return {
	    heads: [{'partNumberHead':'TS'},
    	        {"partNumberHead":"TC"},
    	        {"partNumberHead":"TA"},
    	        {"partNumberHead":"TZ"},
    	        {"partNumberHead":"TSO"}]
	};
});

leatherback.factory('calculator', function() {
	var service = {};
	
	service.calculate = function($scope) {
		$scope.prescription.totalAmount = 0;
		$scope.prescription.totalPrice = 0;
		for(var index = 0; index <= 19; index++) {
			if($scope.amounts[index] != '' && !angular.isNumber($scope.amounts[index])) {
				$scope.prescription.totalAmount += parseFloat($scope.amounts[index]);
			}
		}
		
		for(var index = 0; index <= 19; index++) {
			if($scope.prices[index] != '' && !angular.isNumber($scope.prices[index])) {
				$scope.prescription.totalPrice += parseFloat($scope.prices[index]);
			}
		}
		
		$scope.prescription.totalAmountAfterHanded = $scope.prescription.hand * $scope.prescription.totalAmount;
		if($scope.prescription.totalAmount != 0 && $scope.prescription.totalPrice != 0) {
			$scope.prescription.averageCost = $scope.prescription.totalPrice / $scope.prescription.totalAmount;
		}
	}
	
	return service;
});