import ManagerPath from '@/constants/ManagerPath'
import ResourceURL from '@/constants/ResourceURL'
import { Configs } from '@/types/Configs'

export default class CustomerRoleConfigs extends Configs {
    static readonly managerPath = ManagerPath.CUSTOMER_ROLE
    static readonly resourceUrl = ResourceURL.CUSTOMER_ROLE
    static readonly resourceKey = 'customerRoles'
    static readonly createTitle = 'Create Customer Roles'
    static readonly updateTitle = 'Update Customer Roles'
    static readonly searchTitle = 'Search Customer Roles'
}
