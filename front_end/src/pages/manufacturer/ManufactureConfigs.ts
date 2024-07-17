import ManagerPath from "@/constants/ManagerPath ";
import ResourceURL from "@/constants/ResourceURL ";
import { Configs } from "@/types/Configs";

export default class ManufactureConfigs extends Configs{
    static readonly managerPath = ManagerPath.MANUFACTURE
    static readonly resourceUrl = ResourceURL.MANUFACTURE
    static readonly resourceKey = 'manufacturers'
    static readonly createTitle = 'Create Manufacture'
    static readonly updateTitle = 'Update Manufacture'
    static readonly searchTitle = 'Search Manufacture'
}