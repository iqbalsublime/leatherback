leatherback.controller('mainCtrl', ['$scope','$location','prescriptionService','dialogs', '$modal',
                                    function($scope, $location, prescriptionService, dialogs, $modal) {
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
    
    $scope.remove = function(index, id) {
    	var dlg = dialogs.confirm();
		dlg.result.then(function(btn){
		   	prescriptionService.remove(id).then(function(returnData) {
		    	prescriptionService.list($scope.currentPage).then(function(returnData) {
		            $scope.prescriptions = returnData.data;
		            $scope.setPageStatus(returnData.totalItems, returnData.currentPage);
		        });
	        });
		},function(btn){
			$scope.confirmed = 'You confirmed "No."';
		});
    };
    
    $scope.show = function(id) {
    	var dlg = dialogs.create('/dialogs/custom2.html', 'showCtrl', id, {windowClass:'app-modal-window'});
    }
    
    $scope.edit = function(id) {
    	$location.path('/edit/' + id);
    }
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

leatherback.controller('showCtrl', ['$scope', '$routeParams', '$location', 'prescriptionService', 'data',
                                    function($scope, $routeParams, $location, prescriptionService, data) {
    $scope.prescription = {};
    prescriptionService.getById(data).then(function(returnData) {
        $scope.prescription = returnData;
    });

}]);

leatherback.controller('addCtrl', ['$scope','$location', '$filter', 'prescriptionService', 'partNumberFactory', 
                                   function($scope, $rootScope, $location, $filter, prescriptionService, partNumberFactory) {
	
	$scope.partNumberHeads = partNumberFactory.heads;
	
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
	                    ,'prices[0]', 'prices[1]', 'prices[2]', 'prices[3]', 'prices[4]', 'prices[5]', 'prices[6]', 'prices[7]', 'prices[8]', 'prices[9]', 'prices[10]', 'prices[11]', 'prices[12]', 'prices[13]', 'prices[14]', 'prices[15]', 'prices[16]', 'prices[17]', 'prices[18]', 'prices[19]'
	                    , 'prescription.hand'], function() {
		$scope.calculate();
	});
	

	$scope.calculate = function() {
		$scope.prescription.totalAmount = 0;
		$scope.prescription.totalPrice = 0;
		for(var index = 0; index <= 19; index++) {
			if($scope.amounts[index] != '' && !isNaN($scope.amounts[index])) {
				$scope.prescription.totalAmount += parseFloat($scope.amounts[index]);
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
		if(typeof $scope.prescription.partNumberHead.partNumberHead != 'undefined') {
			$scope.prescription.partNumberHead = $scope.prescription.partNumberHead.partNumberHead;
		}
		$scope.prescription.lotNumber = $scope.lotNumberHead + $scope.lotNumberBody;
		
		$scope.prescription.details = [];
		for(var index = 0; index <= 19; index++) {
			if(typeof $scope.names[index] != 'undefined') {
				if($scope.names[index].trim() != '') {
					$scope.prescription.details.push({
						prescriptionName: $scope.names[index], 
						amount: $scope.amounts[index], 
						price: $scope.prices[index], 
						note: $scope.notes[index]
					});
				}
			}
		}
		
		prescriptionService.create($scope.prescription).then(function(returnData) {
            $location.path('/');
        });
	};
}]);

leatherback.controller('editCtrl', ['$scope','$location', '$filter', 'prescriptionService', 'partNumberFactory', function($scope, $location, $filter, prescriptionService, partNumberFactory) {
	$scope.partNumberHeads = alert(partNumberFactory.heads);
	
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
	                    ,'prices[0]', 'prices[1]', 'prices[2]', 'prices[3]', 'prices[4]', 'prices[5]', 'prices[6]', 'prices[7]', 'prices[8]', 'prices[9]', 'prices[10]', 'prices[11]', 'prices[12]', 'prices[13]', 'prices[14]', 'prices[15]', 'prices[16]', 'prices[17]', 'prices[18]', 'prices[19]'
	                    , 'prescription.hand'], function() {
		$scope.calculate();
	});
	

	$scope.calculate = function() {
		$scope.prescription.totalAmount = 0;
		$scope.prescription.totalPrice = 0;
		for(var index = 0; index <= 19; index++) {
			if($scope.amounts[index] != '' && !isNaN($scope.amounts[index])) {
				$scope.prescription.totalAmount += parseFloat($scope.amounts[index]);
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
		if(typeof $scope.prescription.partNumberHead.partNumberHead != 'undefined') {
			$scope.prescription.partNumberHead = $scope.prescription.partNumberHead.partNumberHead;
		}
		$scope.prescription.lotNumber = $scope.lotNumberHead + $scope.lotNumberBody;
		
		$scope.prescription.details = [];
		for(var index = 0; index <= 19; index++) {
			if(typeof $scope.names[index] != 'undefined') {
				if($scope.names[index].trim() != '') {
					$scope.prescription.details.push({
						prescriptionName: $scope.names[index], 
						amount: $scope.amounts[index], 
						price: $scope.prices[index], 
						note: $scope.notes[index]
					});
				}
			}
		}
		
		prescriptionService.create($scope.prescription).then(function(returnData) {
            $location.path('/');
        });
	};
}]);
