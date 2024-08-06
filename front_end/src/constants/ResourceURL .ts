import ApplicationConstants from './ApplicationConstants '

const apiPath = ApplicationConstants.API_PATH
const clientApiPath = ApplicationConstants.CLIENT_API_PATH

class ResourceURL {
    // ADMIN
    static readonly PRODUCT = apiPath + '/products'
    static readonly PRODUCT_TAG = apiPath + '/product-tags'
    static readonly PRODUCT_ATTRIBUTE = apiPath + '/product-attributes'
    static readonly PREDEFINED_PRODUCT_ATTRIBUTE_VALUE = apiPath + '/predefined-product-attribute-values'
    static readonly CATEGORY = apiPath + '/categories'
    static readonly PICTURE = apiPath + '/picture'
    static readonly BRAND = apiPath + '/brands'
    static readonly ORDER = apiPath + '/orders'
    static readonly SPECIFICATION_ATTRIBUTE_GROUP = apiPath + '/specification-attribute-groups'
    static readonly SPECIFICATION_ATTRIBUTE = apiPath + '/specification-attributes'
    static readonly SPECIFICATION_ATTRIBUTE_OPTION = apiPath + '/specification-attribute-options'
    static readonly USE_GET_UNGROUPED_ATTRIBUTES = apiPath + '/specification-attributes/no-group-or-invalid'
    static readonly PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING = apiPath + '/product-specification-attribute-mappings'
    static readonly GET_PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_BY_PRODUCT_ID =
        apiPath + '/product-specification-attribute-mappings/by-product'
    // CLIENT
    static readonly CLIENT_CATEGORY = clientApiPath + '/categories'
    static readonly CLIENT_PRODUCT = clientApiPath + '/products'
    static readonly CLIENT_MANUFACTURE = apiPath + '/manufactures'

    // AUTHENTICATION
    static readonly LOGIN = apiPath + '/auth/login'
    static readonly ADMIN_USER_INFO = apiPath + '/auth/info'
    static readonly CLIENT_REGISTRATION = apiPath + '/auth/registration'
    static readonly CLIENT_REGISTRATION_RESEND_TOKEN = (userId: number) =>
        apiPath + `/auth/registration/${userId}/resend-token`
    static readonly CLIENT_REGISTRATION_CONFIRM = apiPath + '/auth/registration/confirm'
    static readonly CLIENT_REGISTRATION_CHANGE_EMAIL = (userId: number) =>
        apiPath + `/auth/registration/${userId}/change-email`
    static readonly CLIENT_FORGOT_PASSWORD = apiPath + '/auth/forgot-password'
    static readonly CLIENT_RESET_PASSWORD = apiPath + '/auth/reset-password'
}

export default ResourceURL
