import { Configs } from '@/types/Configs'
import ManagerPath from '@/constants/ManagerPath'
import ResourceURL from '@/constants/ResourceURL'

export default class SpecificationAttributeGroupConfigs extends Configs {
    static readonly managerPath = ManagerPath.SPECIFICATION_ATTRIBUTE
    static readonly resourceUrl = ResourceURL.SPECIFICATION_ATTRIBUTE_GROUP
    static readonly resourceGetUngroupedAttributes = ResourceURL.USE_GET_UNGROUPED_ATTRIBUTES
    static readonly resourceGetUngroupedAttributesKey = 'resourceGetUngroupedAttributes'
    static readonly resourceKey = 'specificationAttributeGroups'
    static readonly addBtn = 'Add Group'
    static readonly updateTitle = 'Specification attribute group details'
    static readonly searchTitle = 'Search'
    static readonly btnSave = 'Save'
    static readonly btnDelete = 'Delete'
    static readonly manageTitle = 'Specification attributes'
    static readonly listGroupTitle = 'Grouped Specification Attributes'
    static readonly listUnGroupTitle = 'Ungrouped Specification Attributes'
    static readonly addTitle = 'Add specification attribute group'
}
