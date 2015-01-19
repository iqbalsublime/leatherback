leatherback.config(function($translateProvider) {
  $translateProvider.translations('en', {
	  DATE: 'Date',
	  LOT_NUMBER: 'Lot number',
	  PART_NUMBER: 'Part number',
	  TOTAL_AMOUNT: 'Total amount',
	  TOTAL_AMOUNT_AFTER_HANDED: 'Total amount * hand',
	  AVERAGE_COST: 'Average cost',
	  SEARCH: 'Search'
  })
  .translations('zh', {
	  DATE: '日期',
	  LOT_NUMBER: 'Lot number',
	  PART_NUMBER: 'Part number',
	  TOTAL_AMOUNT: 'Total amount',
	  TOTAL_AMOUNT_AFTER_HANDED: 'Total amount * hand',
	  AVERAGE_COST: 'Average cost',
	  SEARCH: '查尋'
  });
  $translateProvider.preferredLanguage('zh');
});