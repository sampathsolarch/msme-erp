package com.tmlab.erp.constants;

/**
 * @ClassName:BusinessConstants
 * @Description business dictionary class
 * @Author SD
 * @Date 2019-3-6 17:58
 * @Version 1.0
 **/
public class BusinessConstants {

	/**
	 * default date format
	 */
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * Initial time of day
	 */
	public static final String DAY_FIRST_TIME = "00:00:00";
	/**
	 * End of day
	 */
	public static final String DAY_LAST_TIME = "23:59:59";
	/**
	 * Default pagination start page number
	 */
	public static final Integer DEFAULT_PAGINATION_PAGE_NUMBER = 1;
	/**
	 * The default number of data returned by the list when there is no data
	 */
	public static final Long DEFAULT_LIST_NULL_NUMBER = 0L;
	/**
	 * Default number of pagination bars
	 */
	public static final Integer DEFAULT_PAGINATION_PAGE_SIZE = 10;
	/**
	 * Inbound and outbound type of document master table type Inbound Outbound
	 * Others depothead
	 */
	public static final String DEPOTHEAD_TYPE_IN = "Inbound";
	public static final String DEPOTHEAD_TYPE_OUT = "Outbound";
	public static final String DEPOTHEAD_TYPE_OTHER = "Other";
	/**
	 * Payment type payType //Pay Now/Prepay
	 */
	public static final String PAY_TYPE_PREPAID = "Prepayment";
	public static final String PAY_TYPE_BY_CASH = "Pay Now";
	/**
	 * delete flag deleteFlag '0' not deleted '1' deleted
	 */
	public static final String DELETE_FLAG_DELETED = "1";
	public static final String DELETE_FLAG_EXISTS = "0";
	/**
	 * Whether to sell isSell '0' not sold '1' sold
	 */
	public static final String IS_SELL_SELLED = "1";
	public static final String IS_SELL_HOLD = "0";
	/**
	 * Whether the product has serial number identification enabledSerialNumber '0'
	 * is not enabled '1' is enabled
	 */
	public static final String ENABLE_SERIAL_NUMBER_ENABLED = "1";
	public static final String ENABLE_SERIAL_NUMBER_NOT_ENABLED = "0";
	/**
	 * Document status billsStatus '0' Unapproved '1' Approved '2' Purchase
	 * completed | Sales '3' Partial purchase | Sales
	 */
	public static final String BILLS_STATUS_UN_AUDIT = "0";
	public static final String BILLS_STATUS_AUDIT = "1";
	public static final String BILLS_STATUS_SKIPED = "2";
	public static final String BILLS_STATUS_SKIPING = "3";
	/**
	 * Inbound and outbound classification Purchase, purchase return, other, retail,
	 * sales, transfer, inventory review, etc.
	 */
	public static final String SUB_TYPE_PURCHASE_ORDER = "Purchase Order";
	public static final String SUB_TYPE_PURCHASE = "Purchase";
	public static final String SUB_TYPE_PURCHASE_RETURN = "Purchase Return";
	public static final String SUB_TYPE_OTHER = "Other";
	public static final String SUB_TYPE_RETAIL = "Retail";
	public static final String SUB_TYPE_SALES_ORDER = "Sales Order";
	public static final String SUB_TYPE_SALES = "Sales";
	public static final String SUB_TYPE_SALES_RETURN = "Sales Return";
	public static final String SUB_TYPE_TRANSFER = "Transfer";
	public static final String SUB_TYPE_REPLAY = "Inventory replay";
	/**
	 * The maximum number of data bars when inserting sql in batches
	 */
	public static final int BATCH_INSERT_MAX_NUMBER = 500;
	/**
	 * sequence name
	 */
	// sequence returns the minimum length of the string
	public static final Long SEQ_TO_STRING_MIN_LENGTH = 10000000000L;
	// The base value is appended before the sequence length is less than the base
	// length
	public static final String SEQ_TO_STRING_LESS_INSERT = "0";
	// Document Number
	public static final String DEPOT_NUMBER_SEQ = "depot_number_seq";
	/**
	 * Product category root directory id
	 */
	/**
	 * create by: qiankunpingtai create time: 2019/3/14 11:41 description: In order
	 * to allow users to create their own initial directory, set the parent
	 * directory id of the root directory to -1
	 *
	 */
	public static final Long MATERIAL_CATEGORY_ROOT_PARENT_ID = -1L;
	/**
	 * Product category status 0 system default, 1 enable, 2 delete
	 */
	public static final String MATERIAL_CATEGORY_STATUS_DEFAULT = "0";
	public static final String MATERIAL_CATEGORY_STATUS_ENABLE = "1";
	public static final String MATERIAL_CATEGORY_STATUS_DELETE = "2";
	/**
	 * Institution status 1 is not open, 2 is open, 3 is closed, 4 is closed, 5 has
	 * been removed
	 */
	public static final String ORGANIZATION_STCD_NOT_OPEN = "1";
	public static final String ORGANIZATION_STCD_OPEN = "2";
	public static final String ORGANIZATION_STCD_BUSINESS_SUSPENDED = "3";
	public static final String ORGANIZATION_STCD_BUSINESS_TERMINATED = "4";
	public static final String ORGANIZATION_STCD_REMOVED = "5";
	/**
	 * Root institution parent number The parent structure number of the root
	 * machine defaults to -1
	 */
	public static final String ORGANIZATION_ROOT_PARENT_NO = "-1";
	/**
	 * Added user default password
	 */
	public static final String USER_DEFAULT_PASSWORD = "123456";
	/**
	 * Whether the user comes with the system 0, non-system built-in, 1 system
	 * built-in
	 */
	public static final byte USER_NOT_SYSTEM = 0;
	public static final byte USER_IS_SYSTEM = 1;
	/**
	 * Whether the user is an administrator 0, manager, 1 employee
	 */
	public static final byte USER_IS_MANAGER = 0;
	public static final byte USER_NOT_MANAGER = 1;
	/**
	 * user status 0: Normal, 1: Deleted, 2 Banned
	 */
	public static final byte USER_STATUS_NORMAL = 0;
	public static final byte USER_STATUS_DELETE = 1;
	public static final byte USER_STATUS_BANNED = 2;
	/**
	 * log operations Add, modify, delete, log in, import
	 */
	public static final String LOG_OPERATION_TYPE_ADD = "Add";
	public static final String LOG_OPERATION_TYPE_BATCH_ADD = "Batch Add";
	public static final String LOG_OPERATION_TYPE_EDIT = "Modify";
	public static final String LOG_OPERATION_TYPE_DELETE = "Delete";
	public static final String LOG_OPERATION_TYPE_LOGIN = "Login";
	public static final String LOG_OPERATION_TYPE_IMPORT = "Import";

	/**
	 * Data quantity unit strip
	 */
	public static final String LOG_DATA_UNIT = "Article";

	/**
	 * delete type 1 normal delete 2 Forced deletion
	 */
	public static final String DELETE_TYPE_NORMAL = "1";
	public static final String DELETE_TYPE_FORCE = "2";

	/**
	 * Default administrator account
	 */
	public static final String DEFAULT_MANAGER = "admin";

	public static final String ROLE_TYPE_PRIVATE = "Personal Data";

	public static final String ROLE_TYPE_THIS_ORG = "Data of this organization";

	/**
	 * redis related
	 */
	// session life cycle, seconds
	public static final Long MAX_SESSION_IN_SECONDS = 60 * 60 * 24L;
}