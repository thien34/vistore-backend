import ManagerPath from '@/constants/ManagerPath'
import ResourceURL from '@/constants/ResourceURL'
import { Configs } from '@/types/Configs'

export default class CustomerConfigs extends Configs {
    static readonly managerPath = ManagerPath.CUSTOMER
    static readonly resourceUrl = ResourceURL.CUSTOMER
    static readonly resourceKey = 'customers'
    static readonly createTitle = 'Create Customers'
    static readonly updateTitle = 'Update Customers'
    static readonly searchTitle = 'Search Customers'
}
