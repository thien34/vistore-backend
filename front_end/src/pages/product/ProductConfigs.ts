import { Configs } from '@/types/Configs'
import ManagerPath from '@/constants/ManagerPath'
import ResourceURL from '@/constants/ResourceURL'

export default class ProductConfigs extends Configs {
    static readonly managerPath = ManagerPath.PRODUCT
    static readonly resourceUrl = ResourceURL.PRODUCT
    static readonly resourceUrlBySku = ResourceURL.PRODUCT_BY_SKU
    static readonly resourceKey = 'products'
    static readonly createTitle = 'Create Product'
    static readonly updateTitle = 'Update Product'
    static readonly searchTitle = 'Search'
}
