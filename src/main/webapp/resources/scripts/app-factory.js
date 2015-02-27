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
			var amount = Number($scope.amounts[index]);
			if(angular.isNumber(amount)) {
				$scope.prescription.totalAmount += amount;
			}
		}
		$scope.prescription.totalAmount = roundUp($scope.prescription.totalAmount);
		
		for(var index = 0; index <= 19; index++) {
			var price = Number($scope.prices[index]);
			if(angular.isNumber(price)) {
				$scope.prescription.totalPrice += price;
			}
		}
		$scope.prescription.totalPrice = roundUp($scope.prescription.totalPrice);
		
		$scope.prescription.totalAmountAfterHanded = $scope.prescription.hand * $scope.prescription.totalAmount;
		if($scope.prescription.totalAmount != 0 && $scope.prescription.totalPrice != 0) {
			$scope.prescription.averageCost = roundUp($scope.prescription.totalPrice / $scope.prescription.totalAmount);
		}
		
		function roundUp(n) {
			return Math.round(n * 100) / 100;
		}
	}
	
	return service;
});

leatherback.factory('pagination', function() {
	var service = {};
	
	service.setPageStatus = function($scope, totalItems, currentPage) {
        $scope.totalItems = totalItems;
        $scope.currentPage = currentPage;
	}
	
	return service;
});
