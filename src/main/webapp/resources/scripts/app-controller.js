leatherback.controller('mainCtrl', ['$scope','$location','prescriptionService','$modal', 'pagination', 
                                    function($scope, $location, prescriptionService, $modal, pagination) {
	
	$scope.maxSize = 5;
	$scope.currentPage = 1;
	
    $scope.prescriptions = [];
    $scope.$watch('currentPage', function() {
    	prescriptionService.list($scope.currentPage).then(function(returnData) {
            $scope.prescriptions = returnData.data;
            pagination.setPageStatus($scope, returnData.totalItems, returnData.currentPage);
        });
    });
    
    $scope.add = function() {
    	$location.path('/add');
    }
    
    $scope.search = function() {
    	$location.path('/search');
    }
    
    $scope.edit = function(id) {
    	$location.path('/edit/' + id);
    }
    
    $scope.remove = function(index, id) {
    	var dlg = dialogs.confirm();
		dlg.result.then(function(btn){
		   	prescriptionService.remove(id).then(function(returnData) {
		    	prescriptionService.list($scope.currentPage).then(function(returnData) {
		            $scope.prescriptions = returnData.data;
		            pagination.setPageStatus($scope, returnData.totalItems, returnData.currentPage);
		        });
	        });
		},function(btn){
			$scope.confirmed = 'You confirmed "No."';
		});
    };

    $scope.show = function(id) {
    	var modalInstance = $modal.open({
    	      templateUrl: 'showDetails.html',
    	      controller: 'showCtrl',
    	      size: 'lg',
    	      resolve: {
    	        id: function () {
    	          return id;
    	        }
    	      }
    	    });

    	    modalInstance.result.then(function (selectedItem) {
    	      $scope.selected = selectedItem;
    	    }, function () {
    	      $log.info('Modal dismissed at: ' + new Date());
    	    });
    }
}]);

leatherback.controller('showCtrl', ['$scope', '$location', '$modalInstance', 'prescriptionService', 'id',
                                    function($scope, $location, $modalInstance, prescriptionService, id) {
    $scope.prescription = {};
    prescriptionService.getById(id).then(function(returnData) {
        $scope.prescription = returnData;
    });

	$scope.ok = function () {
	    $modalInstance.close($scope.selected.item);
	};
	
	$scope.cancel = function () {
	    $modalInstance.dismiss('cancel');
	};
}]);


leatherback.controller('ModalInstanceCtrl', function ($scope, $modalInstance, items) {

	  $scope.items = items;
	  $scope.selected = {
	    item: $scope.items[0]
	  };

	  $scope.ok = function () {
	    $modalInstance.close($scope.selected.item);
	  };

	  $scope.cancel = function () {
	    $modalInstance.dismiss('cancel');
	  };
	});


leatherback.controller('reportCtrl', ['$scope','$location', '$window', 'partNumberFactory', 'reportService', 'pagination', 
                                      function($scope, $location, $window, partNumberFactory, reportService, pagination) {
	
	$scope.reportQuery = {};
	$scope.searched = false;
	$scope.maxSize = 5;
	$scope.currentPage = 1;
	$scope.partNumberHeads = partNumberFactory.heads;
	
	$scope.open = function($event, opened) {
	    $event.preventDefault();
	    $event.stopPropagation();
	    $scope[opened] = true;
	};

	$scope.dateOptions = {
	    formatYear: 'yy',
	    startingDay: 0
	};
	
//	var today = new Date();
//	$scope.reportQuery.endDate = today;
//	$scope.reportQuery.startDate = today.setDate(today.getDate() - 1);

    $scope.prescriptions = [];
//    $scope.$watch('currentPage', function() {
//    	reportService.query($scope.reportQuery, $scope.currentPage).then(function(returnData) {
//            $scope.prescriptions = returnData.data;
//            pagination.setPageStatus($scope, returnData.totalItems, returnData.currentPage);
//        });
//    });
    
	$scope.search = function() {
		if((typeof $scope.reportQuery.partNumberHead != 'undefined') && (typeof $scope.reportQuery.partNumberHead.partNumberHead != 'undefined')) {
			$scope.reportQuery.partNumberHead = $scope.reportQuery.partNumberHead.partNumberHead;
		}
		
		reportService.query($scope.reportQuery, $scope.currentPage).then(function(returnData) {
            $scope.prescriptions = returnData.data;
            pagination.setPageStatus($scope, returnData.totalItems, returnData.currentPage);
            $scope.searched = true;
        });
	};
	
	$scope.clear = function() {
		$scope.prescriptions = [];
		$scope.reportQuery.startDate = '';
		$scope.reportQuery.endDate = '';
		$scope.reportQuery.lotNumber = '';
		$scope.reportQuery.partNumberHead = '';
		$scope.reportQuery.partNumberBody = '';
		$scope.reportQuery.showPrice = false
		$scope.searched = false;
	}
	
	$scope.exportTo = function() {
		if((typeof $scope.reportQuery.partNumberHead != 'undefined') && (typeof $scope.reportQuery.partNumberHead.partNumberHead != 'undefined')) {
			$scope.reportQuery.partNumberHead = $scope.reportQuery.partNumberHead.partNumberHead;
		}
		
		reportService.exportTo($scope.reportQuery).then(function(returnData) {
            $scope.searched = true;
            $window.open('api/report/pdf/download/' + returnData.drawerKey);
        });
	}
}]);



leatherback.controller('addCtrl', ['$scope','$location', '$filter', '$window', 'prescriptionService', 'partNumberFactory', 'calculator', 
                                   function($scope, $location, $filter, $window, prescriptionService, partNumberFactory, calculator) {
	
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
		$scope.prescription.lotNumber = $filter('date')($scope.prescription.date, 'yyyyMMdd');
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
		calculator.calculate($scope);
	});
	
	$scope.submit = function() {
		if(typeof $scope.prescription.partNumberHead.partNumberHead != 'undefined') {
			$scope.prescription.partNumberHead = $scope.prescription.partNumberHead.partNumberHead;
		}
		
		$scope.prescription.details = [];
		for(var index = 0; index <= 19; index++) {
			if(typeof $scope.names[index] != 'undefined') {
				if($scope.names[index].trim() != '') {
					$scope.prescription.details.push({
						name: $scope.names[index], 
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
	
	$scope.cancel = function() {
		$window.history.back();
	};
}]);

leatherback.controller('editCtrl', ['$scope', '$routeParams','$location', '$filter', '$window', 'prescriptionService', 'partNumberFactory', 'calculator', 
                                   function($scope, $routeParams, $location, $filter, $window, prescriptionService, partNumberFactory, calculator) {
	
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
	
	$scope.$watch('prescription.date', function() {
		$scope.lotNumberHead = $filter('date')($scope.prescription.date, 'yyyyMMdd');
	})
	

	$scope.$watchGroup(['amounts[0]', 'amounts[1]', 'amounts[2]', 'amounts[3]', 'amounts[4]', 'amounts[5]', 'amounts[6]', 'amounts[7]', 'amounts[8]', 'amounts[9]', 'amounts[10]', 'amounts[11]', 'amounts[12]', 'amounts[13]', 'amounts[14]', 'amounts[15]', 'amounts[16]', 'amounts[17]', 'amounts[18]', 'amounts[19]'
	                    ,'prices[0]', 'prices[1]', 'prices[2]', 'prices[3]', 'prices[4]', 'prices[5]', 'prices[6]', 'prices[7]', 'prices[8]', 'prices[9]', 'prices[10]', 'prices[11]', 'prices[12]', 'prices[13]', 'prices[14]', 'prices[15]', 'prices[16]', 'prices[17]', 'prices[18]', 'prices[19]'
	                    , 'prescription.hand'], function() {
		calculator.calculate($scope);
	});
	
	
	$scope.prescription = {};
    prescriptionService.getById($routeParams.id).then(function(returnData) {
        $scope.prescription = returnData;
        $scope.lotNumberHead = $scope.prescription.lotNumber.substring(0, 8);
        $scope.lotNumberBody = $scope.prescription.lotNumber.substring(8, 12);
    	for(var index = 0; index <= $scope.prescription.details.length - 1; index++) {
    		$scope.names[index] = $scope.prescription.details[index].name;
    		$scope.amounts[index] = $scope.prescription.details[index].amount;
    		$scope.prices[index] = $scope.prescription.details[index].price;
    		$scope.notes[index] = $scope.prescription.details[index].note;
    	}
    });

	
	$scope.submit = function() {
		if(typeof $scope.prescription.partNumberHead.partNumberHead != 'undefined') {
			$scope.prescription.partNumberHead = $scope.prescription.partNumberHead.partNumberHead;
		}
		
		$scope.prescription.details = [];
		for(var index = 0; index <= 19; index++) {
			if(typeof $scope.names[index] != 'undefined') {
				if($scope.names[index].trim() != '') {
					$scope.prescription.details.push({
						name: $scope.names[index], 
						amount: $scope.amounts[index], 
						price: $scope.prices[index], 
						note: $scope.notes[index]
					});
				}
			}
		}
		
		prescriptionService.update($routeParams.id, $scope.prescription).then(function(returnData) {
            $location.path('/');
        });
	};
	
	$scope.cancel = function() {
		$window.history.back();
	};
}]);
