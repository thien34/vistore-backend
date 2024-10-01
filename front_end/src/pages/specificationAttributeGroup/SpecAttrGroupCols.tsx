import ManagerPath from '@/constants/ManagerPath'
import { SpecificationAttributeResponse } from '@/model/SpecificationAttribute'
import { SpecificationAttributeGroupResponse } from '@/model/SpecificationAttributeGroup'
import { EditOutlined } from '@ant-design/icons'
import { Button, TableColumnsType } from 'antd'
import { Link } from 'react-router-dom'

export const getSpecAttrGroupCols: TableColumnsType<SpecificationAttributeGroupResponse> = [
    {
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
        sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
        align: 'center',
        width: '15%',
        title: 'Display order',
        dataIndex: 'displayOrder',
        key: 'displayOrder',
        sorter: (a, b) => a.displayOrder - b.displayOrder,
    },
    {
        width: '10%',
        align: 'center',
        title: 'Actions',
        key: 'Detail',
        render: (_, record) => (
            <Link to={`${ManagerPath.SPECIFICATION_ATTRIBUTE_GROUP_UPDATE.replace(':id', record?.id.toString())}`}>
                <Button className='default-btn-color' icon={<EditOutlined />} size='middle'>
                    Detail
                </Button>
            </Link>
        ),
    },
]

export const getSpecAttrUnGroupCols: TableColumnsType<SpecificationAttributeResponse> = [
    {
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
        sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
        align: 'center',
        width: '15%',
        title: 'Display order',
        dataIndex: 'displayOrder',
        key: 'displayOrder',
        sorter: (a, b) => a.displayOrder - b.displayOrder,
    },
    {
        width: '10%',
        align: 'center',
        title: 'Edit',
        key: 'edit',
        render: (record) => (
            <Link to={`${ManagerPath.SPECIFICATION_ATTRIBUTE_UPDATE.replace(':id', record?.id)}`}>
                <Button className='default-btn-color' icon={<EditOutlined />} size='middle'>
                    Edit
                </Button>
            </Link>
        ),
    },
]
