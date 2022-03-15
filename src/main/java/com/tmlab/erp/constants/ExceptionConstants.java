package com.tmlab.erp.constants;

import com.alibaba.fastjson.JSONObject;

public class ExceptionConstants {
	/**
	 * code format type + five digits, such as 3500000 ResourceInfo(value =
	 * "inOutItem", type = 35)
	 *
	 */

	public static final String GLOBAL_RETURNS_CODE = "code";
	public static final String GLOBAL_RETURNS_MESSAGE = "msg";
	public static final String GLOBAL_RETURNS_DATA = "data";

	/**
	 * Normal return/operation succeeded
	 **/
	public static final int SERVICE_SUCCESS_CODE = 200;
	public static final String SERVICE_SUCCESS_MSG = "The operation was successful";
	/**
	 * Data query exception
	 */
	public static final int DATA_READ_FAIL_CODE = 300;
	public static final String DATA_READ_FAIL_MSG = "Data query exception";
	/**
	 * Data write exception
	 */
	public static final int DATA_WRITE_FAIL_CODE = 301;
	public static final String DATA_WRITE_FAIL_MSG = "Data write exception";

	/**
	 * System runtime unknown error
	 **/
	public static final int SERVICE_SYSTEM_ERROR_CODE = 500;
	public static final String SERVICE_SYSTEM_ERROR_MSG = "Unknown exception";

	/**
	 * The delete operation is rejected, please contact the administrator
	 **/
	public static final int DELETE_REFUSED_CODE = 600;
	public static final String DELETE_REFUSED_MSG = "Delete operation denied, please contact administrator";
	/**
	 * Detected that there is dependent data, is it forced to delete it?
	 **/
	public static final int DELETE_FORCE_CONFIRM_CODE = 601;
	public static final String DELETE_FORCE_CONFIRM_MSG = "The existence of dependent data is detected and cannot be deleted!";
	/**
	 * User Info type = 5
	 */
	// failed to add user information
	public static final int USER_ADD_FAILED_CODE = 500000;
	public static final String USER_ADD_FAILED_MSG = "Failed to add user information";
	// Failed to delete user information
	public static final int USER_DELETE_FAILED_CODE = 500001;
	public static final String USER_DELETE_FAILED_MSG = "Failed to delete user information";
	// Failed to modify user information
	public static final int USER_EDIT_FAILED_CODE = 500002;
	public static final String USER_EDIT_FAILED_MSG = "Failed to modify user information";
	// Username already exists
	public static final int USER_USER_NAME_ALREADY_EXISTS_CODE = 500003;
	public static final String USER_USER_NAME_ALREADY_EXISTS_MSG = "Username already exists in this system";
	// login already exists
	public static final int USER_LOGIN_NAME_ALREADY_EXISTS_CODE = 500003;
	public static final String USER_LOGIN_NAME_ALREADY_EXISTS_MSG = "The login name already exists in this system";
	// The number of user entries exceeds the limit
	public static final int USER_OVER_LIMIT_FAILED_CODE = 500004;
	public static final String USER_OVER_LIMIT_FAILED_MSG = "The number of user entries exceeds the limit, please contact the administrator";
	// This username is restricted from use
	public static final int USER_NAME_LIMIT_USE_CODE = 500005;
	public static final String USER_NAME_LIMIT_USE_MSG = "This username is restricted from use";
	// Demo user is not allowed to delete
	public static final int USER_LIMIT_DELETE_CODE = 500006;
	public static final String USER_LIMIT_DELETE_MSG = "Sorry, demo users in demo mode are not allowed to delete";
	// Demo user is not allowed to modify
	public static final int USER_LIMIT_UPDATE_CODE = 500007;
	public static final String USER_LIMIT_UPDATE_MSG = "Sorry, demo users in demo mode are not allowed to modify";
	// Tenant cannot be deleted
	public static final int USER_LIMIT_TENANT_DELETE_CODE = 500008;
	public static final String USER_LIMIT_TENANT_DELETE_MSG = "Sorry, tenant cannot be deleted";

	/**
	 * Character information type = 10
	 */
	// Failed to add role information
	public static final int ROLE_ADD_FAILED_CODE = 1000000;
	public static final String ROLE_ADD_FAILED_MSG = "Failed to add role information";
	// Failed to delete role information
	public static final int ROLE_DELETE_FAILED_CODE = 1000001;
	public static final String ROLE_DELETE_FAILED_MSG = "Failed to delete role information";
	// Failed to modify role information
	public static final int ROLE_EDIT_FAILED_CODE = 1000002;
	public static final String ROLE_EDIT_FAILED_MSG = "Failed to modify role information";
	/**
	 * Application information type = 15
	 */
	// Failed to add role information
	public static final int APP_ADD_FAILED_CODE = 1500000;
	public static final String APP_ADD_FAILED_MSG = "Failed to add application information";
	// Failed to delete role information
	public static final int APP_DELETE_FAILED_CODE = 1500001;
	public static final String APP_DELETE_FAILED_MSG = "Failed to delete application information";
	// Failed to modify role information
	public static final int APP_EDIT_FAILED_CODE = 1500002;
	public static final String APP_EDIT_FAILED_MSG = "Failed to modify application information";
	/**
	 * Warehouse information type = 20
	 */
	// Failed to add warehouse information
	public static final int DEPOT_ADD_FAILED_CODE = 2000000;
	public static final String DEPOT_ADD_FAILED_MSG = "Failed to add warehouse information";
	// Failed to delete warehouse information
	public static final int DEPOT_DELETE_FAILED_CODE = 2000001;
	public static final String DEPOT_DELETE_FAILED_MSG = "Failed to delete warehouse information";
	// Failed to modify warehouse information
	public static final int DEPOT_EDIT_FAILED_CODE = 2000002;
	public static final String DEPOT_EDIT_FAILED_MSG = "Failed to modify warehouse information";

	/**
	 * Function module information type = 30
	 */
	// Failed to add role information
	public static final int FUNCTIONS_ADD_FAILED_CODE = 3000000;
	public static final String FUNCTIONS_ADD_FAILED_MSG = "Failed to add function module information";
	// Failed to delete role information
	public static final int FUNCTIONS_DELETE_FAILED_CODE = 3000001;
	public static final String FUNCTIONS_DELETE_FAILED_MSG = "Failed to delete function module information";
	// Failed to modify role information
	public static final int FUNCTIONS_EDIT_FAILED_CODE = 3000002;
	public static final String FUNCTIONS_EDIT_FAILED_MSG = "Failed to modify function module information";
	/**
	 * Income and Expenditure Item Information type = 35
	 */
	// Failed to add income and expenditure item information
	public static final int IN_OUT_ITEM_ADD_FAILED_CODE = 3500000;
	public static final String IN_OUT_ITEM_ADD_FAILED_MSG = "Failed to add income and expenditure item information";
	// Failed to delete income and expenditure item information
	public static final int IN_OUT_ITEM_DELETE_FAILED_CODE = 3500001;
	public static final String IN_OUT_ITEM_DELETE_FAILED_MSG = "Failed to delete income and expenditure item information";
	// Failed to modify income and expenditure item information
	public static final int IN_OUT_ITEM_EDIT_FAILED_CODE = 3500002;
	public static final String IN_OUT_ITEM_EDIT_FAILED_MSG = "Failed to modify income and expenditure item information";
	/**
	 * Multi-unit information type = 40
	 */
	// Failed to add multi-unit information
	public static final int UNIT_ADD_FAILED_CODE = 4000000;
	public static final String UNIT_ADD_FAILED_MSG = "Failed to add multi-unit information";
	// Failed to delete multi-unit information
	public static final int UNIT_DELETE_FAILED_CODE = 4000001;
	public static final String UNIT_DELETE_FAILED_MSG = "Failed to delete multi-unit information";
	// Failed to modify multi-unit information
	public static final int UNIT_EDIT_FAILED_CODE = 4000002;
	public static final String UNIT_EDIT_FAILED_MSG = "Failed to modify multi-unit information";
	/**
	 * Handler information type = 45
	 */
	// Failed to add handler information
	public static final int PERSON_ADD_FAILED_CODE = 4500000;
	public static final String PERSON_ADD_FAILED_MSG = "Failed to add handler information";
	// Failed to delete handler information
	public static final int PERSON_DELETE_FAILED_CODE = 4500001;
	public static final String PERSON_DELETE_FAILED_MSG = "Failed to delete the handler information";
	// Failed to modify the handler information
	public static final int PERSON_EDIT_FAILED_CODE = 4500002;
	public static final String PERSON_EDIT_FAILED_MSG = "Failed to modify the handler information";
	/**
	 * User role module relationship information type = 50
	 */
	// Failed to add user role module relationship information
	public static final int USER_BUSINESS_ADD_FAILED_CODE = 5000000;
	public static final String USER_BUSINESS_ADD_FAILED_MSG = "Failed to add user role module relationship information";
	// Failed to delete user role module relationship information
	public static final int USER_BUSINESS_DELETE_FAILED_CODE = 5000001;
	public static final String USER_BUSINESS_DELETE_FAILED_MSG = "Failed to delete user role module relationship information";
	// Failed to modify user role module relationship information
	public static final int USER_BUSINESS_EDIT_FAILED_CODE = 5000002;
	public static final String USER_BUSINESS_EDIT_FAILED_MSG = "Failed to modify user role module relationship information";
	/**
	 * System parameter information type = 55
	 */
	// Failed to add system parameter information
	public static final int SYSTEM_CONFIG_ADD_FAILED_CODE = 5500000;
	public static final String SYSTEM_CONFIG_ADD_FAILED_MSG = "Add system parameter information failed";
	// Failed to delete system parameter information
	public static final int SYSTEM_CONFIG_DELETE_FAILED_CODE = 5500001;
	public static final String SYSTEM_CONFIG_DELETE_FAILED_MSG = "Failed to delete system parameter information";
	// Failed to modify system parameter information
	public static final int SYSTEM_CONFIG_EDIT_FAILED_CODE = 5500002;
	public static final String SYSTEM_CONFIG_EDIT_FAILED_MSG = "Failed to modify system parameter information";
	/**
	 * Product extension information type = 60
	 */
	// Failed to add product extension information
	public static final int MATERIAL_PROPERTY_ADD_FAILED_CODE = 6000000;
	public static final String MATERIAL_PROPERTY_ADD_FAILED_MSG = "Failed to add product extension information";
	// Failed to delete product extension information
	public static final int MATERIAL_PROPERTY_DELETE_FAILED_CODE = 6000001;
	public static final String MATERIAL_PROPERTY_DELETE_FAILED_MSG = "Failed to delete product extension information";
	// Failed to modify product extension information
	public static final int MATERIAL_PROPERTY_EDIT_FAILED_CODE = 6000002;
	public static final String MATERIAL_PROPERTY_EDIT_FAILED_MSG = "Failed to modify product extension information";
	/**
	 * account information type = 65
	 */
	// Failed to add account information
	public static final int ACCOUNT_ADD_FAILED_CODE = 6500000;
	public static final String ACCOUNT_ADD_FAILED_MSG = "Add account information failed";
	// Failed to delete account information
	public static final int ACCOUNT_DELETE_FAILED_CODE = 6500001;
	public static final String ACCOUNT_DELETE_FAILED_MSG = "Failed to delete account information";
	// Failed to modify account information
	public static final int ACCOUNT_EDIT_FAILED_CODE = 6500002;
	public static final String ACCOUNT_EDIT_FAILED_MSG = "Failed to modify account information";
	/**
	 * Supplier information type = 70
	 */
	// Failed to add supplier information
	public static final int SUPPLIER_ADD_FAILED_CODE = 7000000;
	public static final String SUPPLIER_ADD_FAILED_MSG = "Add supplier information failed";
	// Failed to delete supplier information
	public static final int SUPPLIER_DELETE_FAILED_CODE = 7000001;
	public static final String SUPPLIER_DELETE_FAILED_MSG = "Failed to delete supplier information";
	// Failed to modify supplier information
	public static final int SUPPLIER_EDIT_FAILED_CODE = 7000002;
	public static final String SUPPLIER_EDIT_FAILED_MSG = "Failed to modify supplier information";
	/**
	 * Product category information type = 75
	 */
	// Failed to add product category information
	public static final int MATERIAL_CATEGORY_ADD_FAILED_CODE = 7500000;
	public static final String MATERIAL_CATEGORY_ADD_FAILED_MSG = "Failed to add product category information";
	// Failed to delete product category information
	public static final int MATERIAL_CATEGORY_DELETE_FAILED_CODE = 7500001;
	public static final String MATERIAL_CATEGORY_DELETE_FAILED_MSG = "Failed to delete product category information";
	// Failed to modify product category information
	public static final int MATERIAL_CATEGORY_EDIT_FAILED_CODE = 7500002;
	public static final String MATERIAL_CATEGORY_EDIT_FAILED_MSG = "Failed to modify product category information";
	// The product category number already exists
	public static final int MATERIAL_CATEGORY_SERIAL_ALREADY_EXISTS_CODE = 7500003;
	public static final String MATERIAL_CATEGORY_SERIAL_ALREADY_EXISTS_MSG = "The product category number already exists";
	// The root category does not support modification
	public static final int MATERIAL_CATEGORY_ROOT_NOT_SUPPORT_EDIT_CODE = 7500004;
	public static final String MATERIAL_CATEGORY_ROOT_NOT_SUPPORT_EDIT_MSG = "The root category does not support modification";
	// The root category does not support deletion
	public static final int MATERIAL_CATEGORY_ROOT_NOT_SUPPORT_DELETE_CODE = 7500005;
	public static final String MATERIAL_CATEGORY_ROOT_NOT_SUPPORT_DELETE_MSG = "The root category does not support deletion";
	// This category has subordinates that are not allowed to delete
	public static final int MATERIAL_CATEGORY_CHILD_NOT_SUPPORT_DELETE_CODE = 7500006;
	public static final String MATERIAL_CATEGORY_CHILD_NOT_SUPPORT_DELETE_MSG = "This category has subordinates that cannot be deleted";
	/**
	 * Product information type = 80
	 */
	// Failed to add product information
	public static final int MATERIAL_ADD_FAILED_CODE = 7500000;
	public static final String MATERIAL_ADD_FAILED_MSG = "Failed to add product information";
	// Failed to delete product information
	public static final int MATERIAL_DELETE_FAILED_CODE = 7500001;
	public static final String MATERIAL_DELETE_FAILED_MSG = "Failed to delete product information";
	// Failed to modify product information
	public static final int MATERIAL_EDIT_FAILED_CODE = 7500002;
	public static final String MATERIAL_EDIT_FAILED_MSG = "Failed to modify product information";
	// Product information does not exist
	public static final int MATERIAL_NOT_EXISTS_CODE = 8000000;
	public static final String MATERIAL_NOT_EXISTS_MSG = "Item information does not exist";
	// The product information is not unique
	public static final int MATERIAL_NOT_ONLY_CODE = 8000001;
	public static final String MATERIAL_NOT_ONLY_MSG = "Commodity information is not unique";
	// The product does not have the serial number turned on
	public static final int MATERIAL_NOT_ENABLE_SERIAL_NUMBER_CODE = 8000002;
	public static final String MATERIAL_NOT_ENABLE_SERIAL_NUMBER_MSG = "The serial number function is not enabled for this product";
	// The number of serial numbers bound to the product is less than or equal to
	// the existing inventory of the product
	public static final int MATERIAL_SERIAL_NUMBERE_NOT_MORE_THAN_STORAGE_CODE = 8000003;
	public static final String MATERIAL_SERIAL_NUMBERE_NOT_MORE_THAN_STORAGE_MSG = "The number of serial numbers bound to this product is greater than or equal to the existing inventory of the product";
	// Insufficient product stock
	public static final int MATERIAL_STOCK_NOT_ENOUGH_CODE = 8000004;
	public static final String MATERIAL_STOCK_NOT_ENOUGH_MSG = "Commodity: %s is out of stock";
	// product barcode is repeated
	public static final int MATERIAL_BARCODE_EXISTS_CODE = 8000005;
	public static final String MATERIAL_BARCODE_EXISTS_MSG = "Commodity barcode: %s duplicate";
	// Commodity-unit does not match
	public static final int MATERIAL_UNIT_MATE_CODE = 8000006;
	public static final String MATERIAL_UNIT_MATE_MSG = "Sorry, the unit of the product barcode: %s does not match, please complete the measurement unit information!";
	/**
	 * Document information type = 85
	 */
	// Failed to add document information
	public static final int DEPOT_HEAD_ADD_FAILED_CODE = 8500000;
	public static final String DEPOT_HEAD_ADD_FAILED_MSG = "Failed to add document information";
	// Failed to delete document information
	public static final int DEPOT_HEAD_DELETE_FAILED_CODE = 8500001;
	public static final String DEPOT_HEAD_DELETE_FAILED_MSG = "Failed to delete document information";
	// Failed to modify the document information
	public static final int DEPOT_HEAD_EDIT_FAILED_CODE = 8500002;
	public static final String DEPOT_HEAD_EDIT_FAILED_MSG = "Failed to modify document information";
	// Document entry - warehouse cannot be empty
	public static final int DEPOT_HEAD_DEPOT_FAILED_CODE = 8500004;
	public static final String DEPOT_HEAD_DEPOT_FAILED_MSG = "The warehouse cannot be empty";
	// Document entry - transfer into warehouse cannot be empty
	public static final int DEPOT_HEAD_ANOTHER_DEPOT_FAILED_CODE = 8500005;
	public static final String DEPOT_HEAD_ANOTHER_DEPOT_FAILED_MSG = "The warehouse cannot be empty";
	// Document entry - details cannot be empty
	public static final int DEPOT_HEAD_ROW_FAILED_CODE = 8500006;
	public static final String DEPOT_HEAD_ROW_FAILED_MSG = "Document details cannot be empty";
	// Document entry - account cannot be empty
	public static final int DEPOT_HEAD_ACCOUNT_FAILED_CODE = 8500007;
	public static final String DEPOT_HEAD_ACCOUNT_FAILED_MSG = "The settlement account cannot be empty";
	// Document entry - please modify the settlement amount of multiple accounts
	public static final int DEPOT_HEAD_MANY_ACCOUNT_FAILED_CODE = 8500008;
	public static final String DEPOT_HEAD_MANY_ACCOUNT_FAILED_MSG = "Please modify the settlement amount of multiple accounts";
	// Document entry - return order cannot owe money
	public static final int DEPOT_HEAD_BACK_BILL_DEBT_FAILED_CODE = 8500009;
	public static final String DEPOT_HEAD_BACK_BILL_DEBT_FAILED_MSG = "The return order cannot be owed";
	// Document entry - the transferred warehouse and the original warehouse cannot
	// be duplicated
	public static final int DEPOT_HEAD_ANOTHER_DEPOT_EQUAL_FAILED_CODE = 8500010;
	public static final String DEPOT_HEAD_ANOTHER_DEPOT_EQUAL_FAILED_MSG = "The imported warehouse cannot be duplicated with the original warehouse";
	// Document deletion - only unapproved documents can be deleted
	public static final int DEPOT_HEAD_UN_AUDIT_DELETE_FAILED_CODE = 8500011;
	public static final String DEPOT_HEAD_UN_AUDIT_DELETE_FAILED_MSG = "Sorry, only unapproved documents can be deleted";
	// Document review - only unreviewed documents can be reviewed
	public static final int DEPOT_HEAD_UN_AUDIT_TO_AUDIT_FAILED_CODE = 8500012;
	public static final String DEPOT_HEAD_UN_AUDIT_TO_AUDIT_FAILED_MSG = "Sorry, only unaudited documents can be audited";
	// Document de-approval - only approved documents can be de-approved
	public static final int DEPOT_HEAD_AUDIT_TO_UN_AUDIT_FAILED_CODE = 8500013;
	public static final String DEPOT_HEAD_AUDIT_TO_UN_AUDIT_FAILED_MSG = "Sorry, only approved documents can be de-approved";
	// Document entry - the quantity of commodity barcode XXX needs to be modified
	public static final int DEPOT_HEAD_NUMBER_NEED_EDIT_FAILED_CODE = 85000014;
	public static final String DEPOT_HEAD_NUMBER_NEED_EDIT_FAILED_MSG = "The number of product barcode %s needs to be modified";
	/**
	 * Document details type = 90
	 */
	// Failed to add document details
	public static final int DEPOT_ITEM_ADD_FAILED_CODE = 9000000;
	public static final String DEPOT_ITEM_ADD_FAILED_MSG = "Failed to add document details";
	// Failed to delete document details
	public static final int DEPOT_ITEM_DELETE_FAILED_CODE = 9000001;
	public static final String DEPOT_ITEM_DELETE_FAILED_MSG = "Failed to delete document details";
	// Failed to modify the document details
	public static final int DEPOT_ITEM_EDIT_FAILED_CODE = 9000002;
	public static final String DEPOT_ITEM_EDIT_FAILED_MSG = "Failed to modify document details";
	/**
	 * Financial Information type = 95
	 */
	// failed to add financial information
	public static final int ACCOUNT_HEAD_ADD_FAILED_CODE = 9500000;
	public static final String ACCOUNT_HEAD_ADD_FAILED_MSG = "Failed to add financial information";
	// Failed to delete financial information
	public static final int ACCOUNT_HEAD_DELETE_FAILED_CODE = 9500001;
	public static final String ACCOUNT_HEAD_DELETE_FAILED_MSG = "Failed to delete financial information";
	// Failed to modify financial information
	public static final int ACCOUNT_HEAD_EDIT_FAILED_CODE = 9500002;
	public static final String ACCOUNT_HEAD_EDIT_FAILED_MSG = "Failed to modify financial information";
	// Document entry - details cannot be empty
	public static final int ACCOUNT_HEAD_ROW_FAILED_CODE = 9500003;
	public static final String ACCOUNT_HEAD_ROW_FAILED_MSG = "Document details cannot be empty";
	// Document deletion - only unapproved documents can be deleted
	public static final int ACCOUNT_HEAD_UN_AUDIT_DELETE_FAILED_CODE = 9500004;
	public static final String ACCOUNT_HEAD_UN_AUDIT_DELETE_FAILED_MSG = "Sorry, only unapproved documents can be deleted";
	/**
	 * Financial details type = 100
	 */
	// Failed to add financial details
	public static final int ACCOUNT_ITEM_ADD_FAILED_CODE = 10000000;
	public static final String ACCOUNT_ITEM_ADD_FAILED_MSG = "Failed to add financial details";
	// Failed to delete financial details
	public static final int ACCOUNT_ITEM_DELETE_FAILED_CODE = 10000001;
	public static final String ACCOUNT_ITEM_DELETE_FAILED_MSG = "Failed to delete financial details";
	// Failed to modify financial details
	public static final int ACCOUNT_ITEM_EDIT_FAILED_CODE = 10000002;
	public static final String ACCOUNT_ITEM_EDIT_FAILED_MSG = "Failed to modify financial details";
	/**
	 * serial number type = 105
	 */
	/** Serial number already exists */
	public static final int SERIAL_NUMBERE_ALREADY_EXISTS_CODE = 10500000;
	public static final String SERIAL_NUMBERE_ALREADY_EXISTS_MSG = "Serial number already exists";
	/** The serial number cannot be empty */
	public static final int SERIAL_NUMBERE_NOT_BE_EMPTY_CODE = 10500001;
	public static final String SERIAL_NUMBERE_NOT_BE_EMPTY_MSG = "The serial number cannot be empty";
	/**
	 * The serial number of the product %s is not enough, please refill it and try
	 * again
	 */
	public static final int MATERIAL_SERIAL_NUMBERE_NOT_ENOUGH_CODE = 10500002;
	public static final String MATERIAL_SERIAL_NUMBERE_NOT_ENOUGH_MSG = "Commodity: The serial number under %s is not enough, please add it and try again";
	/** Delete serial number information failed */
	public static final int SERIAL_NUMBERE_DELETE_FAILED_CODE = 10500003;
	public static final String SERIAL_NUMBERE_DELETE_FAILED_MSG = "Failed to delete serial number information";
	/**
	 * Institutional information type = 110
	 */
	// Failed to add organization information
	public static final int ORGANIZATION_ADD_FAILED_CODE = 11000000;
	public static final String ORGANIZATION_ADD_FAILED_MSG = "Failed to add organization information";
	// Failed to delete organization information
	public static final int ORGANIZATION_DELETE_FAILED_CODE = 11000001;
	public static final String ORGANIZATION_DELETE_FAILED_MSG = "Failed to delete organization information";
	// Failed to modify organization information
	public static final int ORGANIZATION_EDIT_FAILED_CODE = 11000002;
	public static final String ORGANIZATION_EDIT_FAILED_MSG = "Failed to modify organization information";
	// Organization ID already exists
	public static final int ORGANIZATION_NO_ALREADY_EXISTS_CODE = 11000003;
	public static final String ORGANIZATION_NO_ALREADY_EXISTS_MSG = "Organization number already exists";
	// The root authority is not allowed to delete
	public static final int ORGANIZATION_ROOT_NOT_ALLOWED_DELETE_CODE = 11000004;
	public static final String ORGANIZATION_ROOT_NOT_ALLOWED_DELETE_MSG = "The root organization is not allowed to delete";
	// The root authority is not allowed to modify
	public static final int ORGANIZATION_ROOT_NOT_ALLOWED_EDIT_CODE = 11000005;
	public static final String ORGANIZATION_ROOT_NOT_ALLOWED_EDIT_MSG = "The root organization is not allowed to be modified";
	// The organization has subordinates that are not allowed to delete
	public static final int ORGANIZATION_CHILD_NOT_ALLOWED_DELETE_CODE = 11000006;
	public static final String ORGANIZATION_CHILD_NOT_ALLOWED_DELETE_MSG = "Deletion is not allowed for the existence of subordinates in this organization";
	/**
	 * Institutional user relationship type = 115
	 */
	// Failed to add organization user association
	public static final int ORGA_USER_REL_ADD_FAILED_CODE = 11500000;
	public static final String ORGA_USER_REL_ADD_FAILED_MSG = "Failed to add organization user association";
	// Failed to delete organization user association
	public static final int ORGA_USER_REL_DELETE_FAILED_CODE = 11500001;
	public static final String ORGA_USER_REL_DELETE_FAILED_MSG = "Failed to delete organization user association";
	// Failed to modify organization user association
	public static final int ORGA_USER_REL_EDIT_FAILED_CODE = 11500002;
	public static final String ORGA_USER_REL_EDIT_FAILED_MSG = "Failed to modify organization user relationship";

	// Demonstrate user prohibited operation
	public static final int SYSTEM_CONFIG_TEST_USER_CODE = -1;
	public static final String SYSTEM_CONFIG_TEST_USER_MSG = "Demo user prohibited operation";

	/**
	 * Standard normal return/operation successful return
	 * 
	 * @return
	 */
	public static JSONObject standardSuccess() {
		JSONObject success = new JSONObject();
		success.put(GLOBAL_RETURNS_CODE, SERVICE_SUCCESS_CODE);
		success.put(GLOBAL_RETURNS_MESSAGE, SERVICE_SUCCESS_MSG);
		return success;
	}

	public static JSONObject standardErrorUserOver() {
		JSONObject success = new JSONObject();
		success.put(GLOBAL_RETURNS_CODE, USER_OVER_LIMIT_FAILED_CODE);
		success.put(GLOBAL_RETURNS_MESSAGE, USER_OVER_LIMIT_FAILED_MSG);
		return success;
	}
}
