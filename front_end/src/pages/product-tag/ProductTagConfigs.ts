import ManagerPath from '@/constants/ManagerPath '
import ResourceURL from '@/constants/ResourceURL '
import { Configs } from '@/types/Configs'

class ProductTagConfigs extends Configs {
    static managerPath = ManagerPath.PRODUCT_TAG
    static resourceUrl = ResourceURL.PRODUCT_TAG
    static resourceKey = 'product-tags'
    static createTitle = 'Create Product Tag'
    static updateTitle = 'Update Product Tag'
    static searchTitle = 'Search'
}

export default ProductTagConfigs
