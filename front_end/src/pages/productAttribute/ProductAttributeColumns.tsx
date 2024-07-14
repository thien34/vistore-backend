import { EditOutlined } from '@ant-design/icons'
import { Button, TableColumnsType } from 'antd'
import { ProductAttributeResponse } from '@/model/ProductAttribute.ts'
// import { Link } from 'react-router-dom'

// function getCategoryFullName(category: CategoriesResponse | CategoryParentResponse): string {
//     if (!category.categoryParent) {
//         return `${category.name}`
//     }
//     return `${getCategoryFullName(category.categoryParent)} >> ${category.name}`
// }

const getProductAttributeColumns = ({ setShowList }): TableColumnsType<ProductAttributeResponse> => [
    {
        width: '40%',
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
        // render: (_, record) => getCategoryFullName(record),
        // sorter: (a, b) => {
        //     const aName = a.categoryParent ? `${a.categoryParent.name} >> ${a.name}` : a.name
        //     const bName = b.categoryParent ? `${b.categoryParent.name} >> ${b.name}` : b.name
        //     return aName.localeCompare(bName)
        // },
    },
    {
        width: '50%',
        title: 'Description',
        dataIndex: 'description',
        key: 'description',
        // render: (published) => (published ? 'Yes' : 'No'),
    },
    // {
    //     width: '25%',
    //     title: 'Display Order',
    //     dataIndex: 'displayOrder',
    //     key: 'displayOrder',
    //     sorter: (a, b) => a.displayOrder - b.displayOrder,
    // },
    {
        width: '10%',
        align: 'center',
        title: 'Action',
        key: 'action',
        render: (_, record) => (
            <Button
                onClick={() => setShowList(record)}
                className='bg-[#374151] border-[#374151] text-white'
                icon={<EditOutlined />}
            >
                Xem chi tiết
            </Button>
        ),
    },
]

export default getProductAttributeColumns
