import { Configs } from '@/types/Configs'
import ManagerPath from '@/constants/ManagerPath'
import ResourceURL from '@/constants/ResourceURL'

export default class ProductPictureMappingConfigs extends Configs {
    static readonly managerPath = ManagerPath.PRODUCT_PICTURE_MAPPING
    static readonly resourceUrl = ResourceURL.PRODUCT_PICTURE_MAPPING
    static readonly resourceKey = 'productPictureMappings'
    static readonly createTitle = 'Create Product Picture Mapping '
    static readonly updateTitle = 'Update Product Picture Mapping '
    static readonly searchTitle = 'Search'
}
