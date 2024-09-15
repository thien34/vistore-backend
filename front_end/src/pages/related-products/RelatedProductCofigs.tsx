import ManagerPath from "@/constants/ManagerPath"
import ResourceURL from "@/constants/ResourceURL"
import { Configs } from "@/types/Configs"

export default class RelatedProductConfigs extends Configs {
    static readonly managerPath = ManagerPath.RELATED_PRODUCT
    static readonly resourceUrl = ResourceURL.RELATED_PRODUCT
    static readonly resourceKey = 'related products'
    static readonly createTitle = 'Create Related Products'
    static readonly updateTitle = 'Update Related Products'
     static readonly ADD_RELATED_PRODUCT = 'Add new related product'
}