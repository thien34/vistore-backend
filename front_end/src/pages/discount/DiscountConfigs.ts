import ManagerPath from '@/constants/ManagerPath'
import ResourceURL from '@/constants/ResourceURL'
import {Configs} from '@/types/Configs'

export default class DiscountConfigs extends Configs {
    static readonly managerPath = ManagerPath.DISCOUNT
    static readonly resourceUrl = ResourceURL.DISCOUNT
    static readonly resourceUrlListName = ResourceURL.DISCOUNT_LIST_NAME
    static readonly resourceKey = 'discounts'
    static readonly createTitle = 'Create Discount'
    static readonly updateTitle = 'Update Discount'
    static readonly searchTitle = 'Search Discount'
    static readonly dateTimeUTC = 'DD/MM/YYYY HH:mm:ss'
}
