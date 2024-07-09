import ManagerPath from '@/constants/ManagerPath '
import ResourceURL from '@/constants/ResourceURL '
import { Configs } from '@/types/Configs'

export default class ProductTagConfigs extends Configs {
    static readonly managerPath = ManagerPath.PRODUCT_TAG
    static readonly resourceUrl = ResourceURL.PRODUCT_TAG
    static readonly resourceKey = 'product-tags'
    static readonly createTitle = 'Create Product Tag'
    static readonly updateTitle = 'Update Product Tag'
    static readonly searchTitle = 'Search'
}
