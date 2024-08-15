import { Configs } from '@/types/Configs'
import ManagerPath from '@/constants/ManagerPath .ts'
import ResourceURL from '@/constants/ResourceURL .ts'

export default class ProductSpecificationAttributeMappingConfigs extends Configs {
    static readonly managerPath = ManagerPath.PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING
    static readonly resourceUrl = ResourceURL.PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING
    static readonly resourceGetByProductId = ResourceURL.GET_PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_BY_PRODUCT_ID
    static readonly resourceKey = 'productSpecificationAttributeMappings'
    static readonly createTitle = 'Create Product Specification Attribute Mapping'
    static readonly updateTitle = 'Update Product Specification Attribute Mapping'
    static readonly searchTitle = 'Search'
}
