import { Configs } from '@/types/Configs'
import ManagerPath from '@/constants/ManagerPath'
import ResourceURL from '@/constants/ResourceURL'

export default class ProductVideoMappingConfigs extends Configs {
    static readonly managerPath = ManagerPath.PRODUCT_VIDEO_MAPPING
    static readonly resourceUrl = ResourceURL.PRODUCT_VIDEO_MAPPING
    static readonly resourceKey = 'productVideoMappings'
    static readonly createTitle = 'Create Product Video Mapping '
    static readonly updateTitle = 'Update Product Video Mapping '
    static readonly searchTitle = 'Search'
}
