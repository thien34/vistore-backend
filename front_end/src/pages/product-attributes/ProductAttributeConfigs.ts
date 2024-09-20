import { Configs } from '@/types/Configs'
import ManagerPath from '@/constants/ManagerPath'
import ResourceURL from '@/constants/ResourceURL'

export default class ProductAttributeConfigs extends Configs {
    static readonly managerPath = ManagerPath.PRODUCT_ATTRIBUTE
    static readonly resourceUrl = ResourceURL.PRODUCT_ATTRIBUTE
    static readonly resourceKey = 'productAttributes'
    static readonly updateTitle = 'Update Product Attribute'
    static readonly searchTitle = 'Search'
    static readonly saveBtn = 'Save'
    static readonly saveTitle = 'Add new product attribute'
    static readonly predefinedTitle = 'Predefined values'
    static readonly addPredefinedBtn = 'Add New Value'
    static readonly editProdAttrTitle = 'Product attribute details'
}
