import ResourceURL from '@/constants/ResourceURL'
import { Configs } from '@/types/Configs'

export default class DiscountConfigs extends Configs {
    static readonly resourceUrl = ResourceURL.DISCOUNT
    static readonly resourceUrlListName = ResourceURL.DISCOUNT_LIST_NAME
    static readonly resourceKey = 'discounts'
}
