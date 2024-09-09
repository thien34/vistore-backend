import { Configs } from '@/types/Configs'
import ManagerPath from '@/constants/ManagerPath'
import ResourceURL from '@/constants/ResourceURL'

export default class SpecificationAttributeConfigs extends Configs {
    static readonly managerPath = ManagerPath.SPECIFICATION_ATTRIBUTE
    static readonly resourceUrl = ResourceURL.SPECIFICATION_ATTRIBUTE
    static readonly resourceKey = 'specificationAttributes'
    static readonly createTitle = 'Create Specification Attribute'
    static readonly updateTitle = 'Update Specification Attribute'
    static readonly searchTitle = 'Search'
    static readonly btnSave = 'Save'
    static readonly btnDelete = 'Delete'
    static readonly addTitle = 'Add specification attribute'
}
