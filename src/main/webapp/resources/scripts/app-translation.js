leatherback.config(function($translateProvider) {
	$translateProvider.translations('en', {
		COMPANY_NAME: 'EVER POLYMER CO., LTD.',
		PRESCRIPTION_MANAGE: 'Prescription',
		REPORT_OUTPUT: 'Report',
		AUTHORISATION_MANAGE: 'Authorisation',
		CHANGE_PASSWORD: 'Change Password',
		LOG_OUT: 'Log out',
		DATE: 'Date',
		START_DATE: 'Start Date',
		END_DATE: 'End Date',
		LOT_NUMBER: 'Lot Number',
		PART_NUMBER: 'Part Number',
		TOTAL_AMOUNT: 'Total Amount',
		TOTAL_PRICE: 'Total Price',
		TOTAL_AMOUNT_AFTER_HANDED: 'Total Amount * Hand',
		AVERAGE_COST: 'Average Cost',
		ADD_A_PRESCRIPTION: 'Add a Prescription',
		EDIT_A_PRESCRIPTION: 'Modify a Prescription',
		SEARCH_PRESCRIPTIONS: 'Search Prescriptions',
		SEARCH: 'Search',
		SEARCH_CLEAR: 'Clear The Search Result',
		MODIFY: 'Modify',
	  	DELETE: 'Delete',
	  	DETAILS: 'Details',
	  	FIRST_PAGE: 'First',
	  	PREVIOUS_PAGE: 'Previous',
	  	NEXT_PAGE: 'Next',
	  	LAST_PAGE: 'Last',
	  	PAGE: 'Page',
	  	ITEM: 'Item',
	  	PRESCRIPTION_NAME: 'Prescription Name',
	  	AMOUNT: 'Amount',
	  	PRICE: 'Price',
	  	NOTE: 'Note',
	  	HAND: 'Hand',
	  	CONFIRM: 'Submit',
	  	CANCEL: 'Cancel',
	  	FORMULA_AVERAGE_COST: '(Price / Amount)',
	  	SHOW_PRICE: 'Show price on the report',
	  	EXPORT_REPORT: 'Export to a report',
	  	NEW_PASSWORD: 'New Password',
	  	NEW_PASSWORD_AGAIN: 'New Password Again',
	  	MSG_NEW_PASSWORD: 'Please enter your new password',
	  	MSG_NEW_PASSWORD_AGAIN: 'Please enter your new password again',
	  	SUCCESSFULLY_CHANGED: 'Successfully changed.',
	  	CLOSE: 'Close',
	  	MODIFY_USER: 'Modify a user',
	  	NAME: 'Name',
	  	PRESCRIPTION_CREATION_AUTH: 'Prescription creation authorisation',
	  	PRESCRIPTION_MODIFICATION_AUTH: 'Prescription modification authorisation',
	  	PRESCRIPTION_DELETION_AUTH: 'Prescription deletion authorisation',
	  	PRESCRIPTION_LIST_AUTH: 'Prescription list authorisation',
	  	USERNAME: 'Username',
	  	FUNCTIONS_AUTHORISATION: 'Functions Authorisation',
	  	PRESCRIPTION_AUTHORISATION: 'Prescription Authorisation',
	  	PLEASE_WAIT: 'Submitting',
	  	SUCCESSFULLY_SUBMITTED: 'Completed',
	  	FAILED_TO_SUBMIT: 'There was some errors',
	  	PASSWORD_DOES_NOT_MATCH: 'The passwords does not match',
	  	THE_FIELD_IS_REQUIRED: 'This field is required',
	  	DATA_TOO_LONG: 'The data is too long',
	  	DATA_TOO_SHORT: 'The data is too short'
	})
	
	.translations('zh', {
		COMPANY_NAME: 'EVER POLYMER CO., LTD.',
		PRESCRIPTION_MANAGE: '配方單管理',
		REPORT_OUTPUT: '報表輸出',
		AUTHORISATION_MANAGE: '權限管理',
		CHANGE_PASSWORD: '變更登入密碼',
		LOG_OUT: '登出',
		DATE: '日期',
		START_DATE: '開始日期',
		END_DATE: '結束日期',
		LOT_NUMBER: '批號',
		PART_NUMBER: '成品料號',
		TOTAL_AMOUNT: '總數量',
		TOTAL_PRICE: '總金額',
		TOTAL_AMOUNT_AFTER_HANDED: '數量 * 手',
		AVERAGE_COST: '平均成本',
		ADD_A_PRESCRIPTION: '新增配方單',
		EDIT_A_PRESCRIPTION: '修改配方單',
		SEARCH_PRESCRIPTIONS: '搜尋配方單',
		SEARCH: '搜尋',
		SEARCH_CLEAR: '清除搜尋結果',
	  	MODIFY: '修改',
	  	DELETE: '刪除',
	  	DETAILS: '詳細',
	  	FIRST_PAGE: '第一頁',
	  	PREVIOUS_PAGE: '前一頁',
	  	NEXT_PAGE: '下一頁',
	  	LAST_PAGE: '最後頁',
	  	PAGE: '頁數',
	  	ITEM: '項次',
	  	PRESCRIPTION_NAME: '配方',
	  	AMOUNT: '數量',
	  	PRICE: '單價',
	  	NOTE: '備註',
	  	HAND: '手',
	  	CONFIRM: '確定',
	  	CANCEL: '取消',
	  	FORMULA_AVERAGE_COST: '(單價 / 數量)',
	  	SHOW_PRICE: '顯示單價',
	  	EXPORT_REPORT: '輸出報表',
	  	NEW_PASSWORD: '新密碼',
	  	NEW_PASSWORD_AGAIN: '確認新密碼',
	  	MSG_NEW_PASSWORD: '請輸入新密碼',
	  	MSG_NEW_PASSWORD_AGAIN: '請再次輸入新密碼',
	  	SUCCESSFULLY_CHANGED: '變更成功',
	  	CLOSE: '關閉',
	  	MODIFY_USER: '修改使用者',
	  	NAME: '姓名',
	  	PRESCRIPTION_CREATION_AUTH: '新增配方',
	  	PRESCRIPTION_MODIFICATION_AUTH: '修改配方',
	  	PRESCRIPTION_DELETION_AUTH: '刪除配方',
	  	PRESCRIPTION_LIST_AUTH: '配方查詢',
	  	USERNAME: '帳號',
	  	FUNCTIONS_AUTHORISATION: '功能權限',
	  	PRESCRIPTION_AUTHORISATION: '配方單權限',
	  	PLEASE_WAIT: '請稍後',
	  	SUCCESSFULLY_SUBMITTED: '完成',
	  	FAILED_TO_SUBMIT: '資料傳送失敗',
	  	PASSWORD_DOES_NOT_MATCH: '兩次密碼不相同',
	  	THE_FIELD_IS_REQUIRED: '此欄位必填',
	  	DATA_TOO_LONG: '資料超過限定長度',
	  	DATA_TOO_SHORT: '資料少於限定長度'

	})
    
	$translateProvider.preferredLanguage('zh');
});