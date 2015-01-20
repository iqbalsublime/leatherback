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
	$scope.prescription.totalAmount = 0;
	$scope.prescription.totalPrice = 0;
	$scope.prescription.hand = 1;
	$scope.prescription.totalAmountAfterHanded = 0;
	$scope.prescription.averageCost = 0;
	
	$scope.$watch('prescription.date', function() {
		$scope.lotNumberHead = $filter('date')($scope.prescription.date, 'yyyyMMdd');
	})
	
	// initialise
	$scope.names = [];
	$scope.amounts = [];
	$scope.prices = [];
	$scope.notes = [];
	
	for(var index = 0; index <= 19; index++) {
		$scope.amounts[index] = 0;
	}
	
	for(var index = 0; index <= 19; index++) {
		$scope.prices[index] = 0;
	}
	
	$scope.$watchGroup(['amounts[0]', 'amounts[1]', 'amounts[2]', 'amounts[3]', 'amounts[4]', 'amounts[5]', 'amounts[6]', 'amounts[7]', 'amounts[8]', 'amounts[9]', 'amounts[10]', 'amounts[11]', 'amounts[12]', 'amounts[13]', 'amounts[14]', 'amounts[15]', 'amounts[16]', 'amounts[17]', 'amounts[18]', 'amounts[19]'
	                    ,'prices[0]', 'prices[1]', 'prices[2]', 'prices[3]', 'prices[4]', 'prices[5]', 'prices[6]', 'prices[7]', 'prices[8]', 'prices[9]', 'prices[10]', 'prices[11]', 'prices[12]', 'prices[13]', 'prices[14]', 'prices[15]', 'prices[16]', 'prices[17]', 'prices[18]', 'prices[19]'], function() {
		$scope.calculate();
	});
	
//	$scope.$watch('amounts[0]', function() {
//		$scope.calculate();
//	});
//	
//	$scope.$watch('amounts[1]', function() {
//		$scope.calculate();
//	});
//	
//	$scope.$watch('amounts[2]', function() {
//		$scope.calculate();
//	});
//	
//	$scope.$watch('amounts[3]', function() {
//		$scope.calculate();
//	});
//	
//	$scope.$watch('amounts[4]', function() {
//		$scope.calculate();
//	});
//	
//	$scope.$watch('amounts[5]', function() {
//		$scope.calculate();
//	});
//	
//	$scope.$watch('amounts[6]', function() {
//		$scope.calculate();
//	});
//	
//	$scope.$watch('amounts[7]', function() {
//		$scope.calculate();
//	});
//	
//	$scope.$watch('amounts[8]', function() {
//		$scope.calculate();
//	});
//	
//	$scope.$watch('amounts[9]', function() {
//		$scope.calculate();
//	});
//	
//	$scope.$watch('amounts[10]', function() {
//		$scope.calculate();
//	});
//	
//	$scope.$watch('amounts[11]', function() {
//		$scope.calculate();
//	});
//	
//	$scope.$watch('amounts[12]', function() {
//		$scope.calculate();
//	});
//	
//	$scope.$watch('amounts[13]', function() {
//		$scope.calculate();
//	});
//	
//	$scope.$watch('amounts[14]', function() {
//		$scope.calculate();
//	});
//	
//	$scope.$watch('amounts[15]', function() {
//		$scope.calculate();
//	});
//	
//	$scope.$watch('amounts[16]', function() {
//		$scope.calculate();
//	});
//	
//	$scope.$watch('amounts[17]', function() {
//		$scope.calculate();
//	});
//	
//	$scope.$watch('amounts[18]', function() {
//		$scope.calculate();
//	});
//	
//	$scope.$watch('amounts[19]', function() {
//		$scope.calculate();
//	});
	
	$scope.calculate = function() {
		$scope.prescription.totalAmount = 0;
		$scope.prescription.totalPrice = 0;
		for(var index = 0; index <= 19; index++) {
			if($scope.amounts[index] != '' && !isNaN($scope.amounts[index])) {
				$scope.prescription.totalAmount += Math.round(parseFloat($scope.amounts[index]) * 10) / 10;
			}
		}
		
		for(var index = 0; index <= 19; index++) {
			if($scope.prices[index] != '' && !isNaN($scope.prices[index])) {
				$scope.prescription.totalPrice += parseFloat($scope.prices[index]);
			}
		}
		
		$scope.prescription.totalAmountAfterHanded = $scope.prescription.hand * $scope.prescription.totalAmount;
		if($scope.prescription.totalAmount != 0 && $scope.prescription.totalPrice != 0) {
			$scope.prescription.averageCost = $scope.prescription.totalPrice / $scope.prescription.totalAmount;
		}
		
	}
	
	$scope.submit = function() {
		alert($scope.names[0]);
		alert($scope.names[1]);
		alert($scope.names[2]);
	};
	
}]);