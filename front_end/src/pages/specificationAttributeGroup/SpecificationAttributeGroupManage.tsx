import { Table, Button, Collapse } from 'antd'
import { PlusOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import useSpecificationAttributeGroupManageViewModel from '@/pages/specificationAttributeGroup/SpecificationAttributeGroup.vm.ts'
import ManagerPath from '@/constants/ManagerPath'
import SpecificationAttributeGroupConfigs from './SpecificationAttributeGroupConfigs'

const SpecificationAttributeGroupManage = () => {
    const {
        groupedData,
        ungroupedData,
        groupColumns,
        unGroupColumns,
        groupedFilter,
        ungroupedFilter,
        handleGroupedTableChangeFilter,
        handleUngroupedTableChangeFilter,
    } = useSpecificationAttributeGroupManageViewModel()

    return (
        <>
            <div className='flex justify-between mb-5'>
                <div className='text-2xl font-medium'>
                    <div className=''>{SpecificationAttributeGroupConfigs.manageTitle}</div>
                </div>
                <div className='flex gap-4 flex-wrap'>
                    <Link to={ManagerPath.SPECIFICATION_ATTRIBUTE_GROUP_ADD}>
                        <Button icon={<PlusOutlined />} className='default-btn-color' size='large'>
                            {SpecificationAttributeGroupConfigs.addBtn}
                        </Button>
                    </Link>
                    <Link to={ManagerPath.SPECIFICATION_ATTRIBUTE_ADD}>
                        <Button icon={<PlusOutlined />} className='default-btn-color' size='large'>
                            Add Attribute
                        </Button>
                    </Link>
                </div>
            </div>
            <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6'>
                <Collapse
                    defaultActiveKey={['1']}
                    className='mb-6'
                    items={[
                        {
                            key: '1',
                            label: SpecificationAttributeGroupConfigs.listGroupTitle,
                            children: (
                                <>
                                    {groupedData && (
                                        <Table
                                            columns={groupColumns}
                                            dataSource={groupedData.items}
                                            bordered
                                            rowKey='id'
                                            pagination={{
                                                current: groupedFilter?.pageNo ?? 1,
                                                pageSize: groupedFilter?.pageSize ?? 6,
                                                total: (groupedData.totalPages ?? 1) * (groupedFilter?.pageSize ?? 6),
                                                onChange: (page, pageSize) =>
                                                    handleGroupedTableChangeFilter({
                                                        current: page,
                                                        pageSize: pageSize,
                                                    }),
                                            }}
                                        />
                                    )}
                                </>
                            ),
                        },
                    ]}
                />
                <Collapse
                    defaultActiveKey={['2']}
                    className='mb-6'
                    items={[
                        {
                            key: '2',
                            label: SpecificationAttributeGroupConfigs.listUnGroupTitle,
                            children: (
                                <>
                                    {ungroupedData && (
                                        <Table
                                            columns={unGroupColumns}
                                            dataSource={ungroupedData.items}
                                            bordered
                                            rowKey='id'
                                            pagination={{
                                                current: ungroupedFilter?.pageNo ?? 1,
                                                pageSize: ungroupedFilter?.pageSize ?? 6,
                                                total:
                                                    (ungroupedData.totalPages ?? 1) * (ungroupedFilter?.pageSize ?? 6),
                                                onChange: (page, pageSize) =>
                                                    handleUngroupedTableChangeFilter({
                                                        current: page,
                                                        pageSize: pageSize,
                                                    }),
                                            }}
                                        />
                                    )}
                                </>
                            ),
                        },
                    ]}
                />
            </div>
        </>
    )
}

export default SpecificationAttributeGroupManage
