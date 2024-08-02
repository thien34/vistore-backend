class ManagerPath {
    static readonly PRODUCT = '/admin/products'
    static readonly PRODUCT_TAG = '/admin/product-tags'
    static readonly PRODUCT_ATTRIBUTE = '/admin/product-attributes'
    static readonly PRODUCT_ATTRIBUTE_ADD = '/admin/product-attributes/add'
    static readonly PRODUCT_ATTRIBUTE_UPDATE = '/admin/product-attributes/:id'
    static readonly PREDEFINED_PRODUCT_ATTRIBUTE_VALUE = '/admin/predefined-product-attribute-values'
    static readonly CATEGORY = '/admin/categories'
    static readonly CATEGORY_ADD = '/admin/categories/add'
    static readonly CATEGORY_UPDATE = '/admin/categories/:id/update'
    static readonly PICTURE = '/admin/picture'
    static readonly ORDER = '/admin/orders'
    static readonly MANUFACTURE= '/admin/manufacturers'
    static readonly MANUFACTURE_ADD = '/admin/manufacturers/add'
    static readonly MANUFACTURE_UPDATE = '/admin/manufacturers/:id/update'
}

export default ManagerPath
