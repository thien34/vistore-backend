import { TitleLink } from './TitleLink'

export abstract class Configs {
    static readonly managerPath: string
    static readonly resourceUrl: string
    static readonly resourceKey: string
    static readonly createTitle: string
    static readonly updateTitle: string
    static readonly manageTitle: string
    static readonly manageTitleLinks: TitleLink[]
}
