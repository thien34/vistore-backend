import ManagerPath from '@/constants/ManagerPath '
import ResourceURL from '@/constants/ResourceURL '
import { Configs } from '@/types/Configs'

export default class PictureConfigs extends Configs {
    static readonly managerPath = ManagerPath.PICTURE
    static readonly resourceUrl = ResourceURL.PICTURE
    static readonly resourceKey = 'picture'
}
