import AppActions from '@/constants/AppActions'
import ManagerPath from '@/constants/ManagerPath'
import { CategoriesResponse, CategoryParentResponse } from '@/model/Category'
import { EditOutlined } from '@ant-design/icons'
import { Button, TableColumnsType } from 'antd'
import { Link } from 'react-router-dom'

function getCategoryFullName(category: CategoriesResponse | CategoryParentResponse): string {
    if (!category.categoryParent) {
        return `${category.name}`
    }
    return `${getCategoryFullName(category.categoryParent)} > ${category.name}`
}

const getCategoryColumns = (): TableColumnsType<CategoriesResponse> => [
    {
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
        render: (_, record) => getCategoryFullName(record),
        sorter: (a, b) => {
            const aName = a.categoryParent ? `${a.categoryParent.name} >> ${a.name}` : a.name
            const bName = b.categoryParent ? `${b.categoryParent.name} >> ${b.name}` : b.name
            return aName.localeCompare(bName)
        },
    },
    {
        width: '10%',
        align: 'center',
        title: 'Published',
        dataIndex: 'published',
        key: 'published',
        render: (published) => (published ? 'Yes' : 'No'),
    },
    {
        width: '15%',
        align: 'center',
        title: 'Display Order',
        dataIndex: 'displayOrder',
        key: 'displayOrder',
        sorter: (a, b) => a.displayOrder - b.displayOrder,
    },
    {
        width: '10%',
        align: 'center',
        title: 'Action',
        key: 'action',
        render: (_, record) => (
            <Link to={`${ManagerPath.CATEGORY_UPDATE.replace(':id', String(record.id))}`}>
                <Button className='default-btn-color' icon={<EditOutlined />}>
                    {AppActions.EDIT}
                </Button>
            </Link>
        ),
    },
]

export default getCategoryColumns
