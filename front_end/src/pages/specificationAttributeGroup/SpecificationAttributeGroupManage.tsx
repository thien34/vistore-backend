import { Table, Button, Row, Col, Space, Spin, Modal, Collapse } from 'antd'
import { EditOutlined, PlusOutlined, ReloadOutlined } from '@ant-design/icons'
import { Link, useNavigate } from 'react-router-dom'
import useSpecificationAttributeGroupManageViewModel from '@/pages/specificationAttributeGroup/SpecificationAttributeGroup.vm.ts'

const { Column } = Table
const { Panel } = Collapse

const SpecificationAttributeGroupManage = () => {
    const navigate = useNavigate()
    const {
        groupedData,
        ungroupedData,
        isLoading,
        errorUngrouped,
        handleReload,
        isSpinning,
        handleModalDelete,
        isModalDeleteButtonDisabled,
        isModalVisible,
        showModal,
        handleOk,
        handleEdit,
        handleCancel,
        modalData,
        rowSelection,
        groupedFilter,
        ungroupedFilter,
        handleGroupedTableChangeFilter,
        handleUngroupedTableChangeFilter,
    } = useSpecificationAttributeGroupManageViewModel()

    if (isLoading) {
        return <div>Loading...</div>
    }

    if (errorUngrouped) {
        return <div>Error loading data: {errorUngrouped.message}</div>
    }

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
            <Row justify='space-between' align='middle' style={{ marginBottom: 16 }}>
                <Col></Col>
                <Col>
                    <Space>
                        <Link to='/admin/specification-attribute-groups/add'>
                            <Button icon={<PlusOutlined />} className='text-white' type='primary' size='middle'>
                                Add Group
                            </Button>
                        </Link>
                        <Link to='/admin/specification-attributes/add'>
                            <Button icon={<PlusOutlined />} className='text-white' type='primary' size='middle'>
                                Add Attribute
                            </Button>
                        </Link>
                        <Button icon={<ReloadOutlined />} onClick={handleReload} size='middle'></Button>
                    </Space>
                </Col>
            </Row>
            {isSpinning && (
                <div className='fixed top-0 left-0 w-full h-full flex items-center justify-center bg-white bg-opacity-50 z-50'>
                    <Spin size='large' />
                </div>
            )}
            <Collapse defaultActiveKey={['1']} className='mb-6'>
                <Panel header='Grouped Specification Attributes' key='1'>
                    <Table
                        className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'
                        dataSource={groupedData?.items}
                        bordered
                        rowKey='id'
                        pagination={{
                            current: groupedFilter?.pageNo ?? 1,
                            pageSize: groupedFilter?.pageSize ?? 6,
                            total: (groupedData?.totalPages ?? 1) * (groupedFilter?.pageSize ?? 6),
                            onChange: (page, pageSize) =>
                                handleGroupedTableChangeFilter({ current: page, pageSize: pageSize }),
                        }}
                    >
                        <Column
                            title='Name'
                            dataIndex='name'
                            key='name'
                            sorter={(a, b) => a.name.localeCompare(b.name)}
                            render={(text, record) => (
                                <a onClick={() => navigate(`/admin/specification-attribute-groups/${record.id}`)}>
                                    {text}
                                </a>
                            )}
                        />
                        <Column
                            align={'center'}
                            width='20%'
                            title='Display order'
                            dataIndex='displayOrder'
                            key='displayOrder'
                            sorter={(a, b) => a.displayOrder - b.displayOrder}
                        />
                        <Column
                            width='10%'
                            title='Actions'
                            key='Detail'
                            align={'center'}
                            render={(record) => (
                                <Button type='primary' onClick={() => showModal(record)}>
                                    Detail
                                </Button>
                            )}
                        />
                    </Table>
                </Panel>
            </Collapse>
            <Collapse defaultActiveKey={['2']} className='mb-6'>
                <Panel header='Ungrouped Specification Attributes' key='2'>
                    <Table
                        className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'
                        dataSource={ungroupedData?.items}
                        bordered
                        rowKey='id'
                        pagination={{
                            current: ungroupedFilter?.pageNo ?? 1,
                            pageSize: ungroupedFilter?.pageSize ?? 6,
                            total: (ungroupedData?.totalPages ?? 1) * (ungroupedFilter?.pageSize ?? 6),
                            onChange: (page, pageSize) =>
                                handleUngroupedTableChangeFilter({ current: page, pageSize: pageSize }),
                        }}
                    >
                        <Column
                            title='Name'
                            dataIndex='name'
                            key='name'
                            sorter={(a, b) => a.name.localeCompare(b.name)}
                        />
                        <Column
                            align={'center'}
                            width='20%'
                            title='Display order'
                            dataIndex='displayOrder'
                            key='displayOrder'
                            sorter={(a, b) => a.displayOrder - b.displayOrder}
                        />
                        <Column
                            width='10%'
                            title='Edit'
                            key='edit'
                            align={'center'}
                            render={(record) => (
                                <Link to={`/admin/specification-attributes/${record?.id}/update`}>
                                    <Button type='primary' icon={<EditOutlined />} size='middle'>
                                        Edit
                                    </Button>
                                </Link>
                            )}
                        />
                    </Table>
                </Panel>
            </Collapse>

            <Modal
                width={550}
                title='Specification Attributes'
                open={isModalVisible}
                onOk={handleOk}
                onCancel={handleCancel}
                footer={[
                    <Button key='cancel' onClick={handleCancel}>
                        Cancel
                    </Button>,
                    <Button
                        key='delete'
                        type='primary'
                        danger
                        onClick={handleModalDelete}
                        disabled={isModalDeleteButtonDisabled}
                    >
                        Delete Selected
                    </Button>,
                ]}
            >
                <Table
                    dataSource={modalData}
                    scroll={{ y: 400 }}
                    rowKey='id'
                    pagination={false}
                    rowSelection={rowSelection}
                >
                    <Column
                        title='Name'
                        dataIndex='name'
                        key='name'
                        sorter={(a, b) => a.name.localeCompare(b.name)}
                        sortDirections={['ascend', 'descend']}
                    />
                    <Column
                        title='Display order'
                        dataIndex='displayOrder'
                        key='displayOrder'
                        sorter={(a, b) => a.displayOrder - b.displayOrder}
                        sortDirections={['ascend', 'descend']}
                    />
                    <Column
                        width='20%'
                        title='Edit'
                        key='edit'
                        render={(record) => (
                            <Link to={`/admin/specification-attributes/${record?.id}/update`}>
                                <Button
                                    type='primary'
                                    icon={<EditOutlined />}
                                    size='middle'
                                    onClick={() => handleEdit(record)}
                                >
                                    Edit
                                </Button>
                            </Link>
                        )}
                    />
                </Table>
            </Modal>
        </div>
    )
}

export default SpecificationAttributeGroupManage
