import ManagerPath from '@/constants/ManagerPath '
import ResourceURL from '@/constants/ResourceURL '
import { Configs } from '@/types/Configs'

export default class CategoryConfigs extends Configs {
    static readonly managerPath = ManagerPath.CATEGORY
    static readonly resourceUrl = ResourceURL.CATEGORY
    static readonly resourceKey = 'categories'
    static readonly createTitle = 'Create Category'
    static readonly updateTitle = 'Update Category'
    static readonly searchTitle = 'Search'
}
