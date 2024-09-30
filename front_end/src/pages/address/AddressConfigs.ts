import ManagerPath from '@/constants/ManagerPath.ts'
import ResourceURL from '@/constants/ResourceURL.ts'
import { Configs } from '@/types/Configs.ts'

export default class AddressConfigs extends Configs {
    static readonly managerPath = ManagerPath.ADDRESS
    static readonly resourceUrl = ResourceURL.ADDRESS
    static readonly resourceUrlProvince = ResourceURL.PROVINCE
    static readonly resourceUrlDistrict = ResourceURL.DISTRICT
    static readonly resourceUrlWard = ResourceURL.WARD
    static readonly resourceKey = 'Address'
    static readonly resourceKeyProvince = 'Province'
    static readonly resourceKeyDistrict = 'District'
    static readonly resourceKeyWard = 'Ward'
    static readonly createTitle = 'Create Address'
    static readonly updateTitle = 'Update Address'
    static readonly searchTitle = 'Search Address'
}
