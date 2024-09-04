import ManagerPath from '@/constants/ManagerPath'
import ResourceURL from '@/constants/ResourceURL'
import { Configs } from '@/types/Configs'

export default class ProductAtbCombinationsConfig extends Configs {
    static readonly managerPath = ManagerPath.PRODUCT_TAG
    static readonly resourceUrl = ResourceURL.PRODUCT_COMBINATIONS
    static readonly resourceUrlByProductId = ResourceURL.PRODUCT_COMBINATIONS_BY_PRODUCT_ID
    static readonly resourceKey = 'product-attribute-combinations'
    static readonly resourceUrlByProductIdMapping = ResourceURL.PRODUCT_ATB_MAPPING_PRODUCT
}
