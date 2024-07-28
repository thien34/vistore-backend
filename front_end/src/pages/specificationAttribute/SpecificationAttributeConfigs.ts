import { Configs } from '@/types/Configs'
import ManagerPath from '@/constants/ManagerPath .ts'
import ResourceURL from '@/constants/ResourceURL .ts'

export default class SpecificationAttributeConfigs extends Configs {
    static readonly managerPath = ManagerPath.SPECIFICATION_ATTRIBUTE
    static readonly resourceUrl = ResourceURL.SPECIFICATION_ATTRIBUTE
    static readonly resourceKey = 'specificationAttributes'
    static readonly createTitle = 'Create Specification Attribute'
    static readonly updateTitle = 'Update Specification Attribute'
    static readonly searchTitle = 'Search'
}
