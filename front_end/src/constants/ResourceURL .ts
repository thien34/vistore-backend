import ApplicationConstants from './ApplicationConstants '

const apiPath = ApplicationConstants.API_PATH
const clientApiPath = ApplicationConstants.CLIENT_API_PATH

class ResourceURL {
    // ADMIN

    static PRODUCT = apiPath + '/products'
    static PRODUCT_TAG = apiPath + '/product-tags'
    static CATEGORY = apiPath + '/categories'
    static BRAND = apiPath + '/brands'

    static ORDER = apiPath + '/orders'

    // CLIENT
    static CLIENT_CATEGORY = clientApiPath + '/categories'
    static CLIENT_PRODUCT = clientApiPath + '/products'

    // AUTHENTICATION
    static LOGIN = apiPath + '/auth/login'
    static ADMIN_USER_INFO = apiPath + '/auth/info'
    static CLIENT_REGISTRATION = apiPath + '/auth/registration'
    static CLIENT_REGISTRATION_RESEND_TOKEN = (userId: number) => apiPath + `/auth/registration/${userId}/resend-token`
    static CLIENT_REGISTRATION_CONFIRM = apiPath + '/auth/registration/confirm'
    static CLIENT_REGISTRATION_CHANGE_EMAIL = (userId: number) => apiPath + `/auth/registration/${userId}/change-email`
    static CLIENT_FORGOT_PASSWORD = apiPath + '/auth/forgot-password'
    static CLIENT_RESET_PASSWORD = apiPath + '/auth/reset-password'
}

export default ResourceURL
