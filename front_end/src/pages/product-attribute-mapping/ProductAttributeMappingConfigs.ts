import ManagerPath from '@/constants/ManagerPath '
import ResourceURL from '@/constants/ResourceURL '
import { Configs } from '@/types/Configs'

export default class ProductAttributeMappingConfigs extends Configs {
    static readonly managerPath = ManagerPath.PRODUCT_ATTRIBUTE_MAPPING
    static readonly resourceUrl = ResourceURL.PRODUCT_ATTRIBUTE_MAPPING
    static readonly resourceKey = 'Product attributes'
    static readonly createTitle = 'Create Product attributes'
    static readonly updateTitle = 'Update Product attributes'
    static readonly searchTitle = 'Search'
}
