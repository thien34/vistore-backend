import { Configs } from '@/types/Configs'
import ManagerPath from '@/constants/ManagerPath'
import ResourceURL from '@/constants/ResourceURL'

export default class ProductAttributeConfigs extends Configs {
    static readonly managerPath = ManagerPath.PRODUCT_ATTRIBUTE
    static readonly resourceUrl = ResourceURL.PRODUCT_ATTRIBUTE
    static readonly resourceKey = 'productAttributes'
    static readonly createTitle = 'Create Product Attribute'
    static readonly updateTitle = 'Update Product Attribute'
    static readonly searchTitle = 'Search'
}
