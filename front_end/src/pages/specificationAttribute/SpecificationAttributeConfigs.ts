import { Configs } from '@/types/Configs'
import ManagerPath from '@/constants/ManagerPath'
import ResourceURL from '@/constants/ResourceURL'

export default class SpecificationAttributeConfigs extends Configs {
    static readonly managerPath = ManagerPath.SPECIFICATION_ATTRIBUTE
    static readonly resourceUrl = ResourceURL.SPECIFICATION_ATTRIBUTE
    static readonly resourceKey = 'specificationAttributes'
    static readonly resourceKeyGroup = 'specificationAttributeGroups'
    static readonly createTitle = 'Create Specification Attribute'
    static readonly updateTitle = 'Update Specification Attribute'
    static readonly searchTitle = 'Search'
    static readonly btnSave = 'Save'
    static readonly btnDelete = 'Delete Specification Attribute'
    static readonly addTitle = 'Add specification attribute'
    static readonly addOptionBtn = 'Add new option'
    static readonly editTitle = 'Specification attribute details'
    static readonly optionsTitle = 'Options'
}
