import { Table, Button, Row, Col, Space, Spin, Pagination, Modal } from 'antd'
import { EditOutlined, PlusOutlined, ReloadOutlined } from '@ant-design/icons'
import { Link, useNavigate } from 'react-router-dom'
import useSpecificationAttributeGroupManageViewModel from '@/pages/specificationAttributeGroup/SpecificationAttributeGroup.vm.ts'
const { Column } = Table

const SpecificationAttributeGroupManage = () => {
    const navigate = useNavigate()
    const {
        dataSource,
        isLoading,
        error,
        handleEdit,
        handleReload,
        isSpinning,
        current,
        onChange,
        ungroupedAttributesData,
        handleModalDelete,
        isModalDeleteButtonDisabled,
        isModalVisible,
        showModal,
        handleOk,
        handleCancel,
        modalData,
        rowSelection,
    } = useSpecificationAttributeGroupManageViewModel()

    if (isLoading) {
        return <div>Loading...</div>
    }

    if (error) {
        return <div>Error loading data: {error}</div>
    }

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
            <Row justify='space-between' align='middle' style={{ marginBottom: 16 }}>
                <Col>
                    <h1>Specification attributes</h1>
                </Col>
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
            <div className='max-h-[500px]'>
                <Table
                    scroll={{ y: 400, x: 500 }}
                    className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'
                    dataSource={dataSource}
                    bordered
                    rowKey='id'
                    pagination={false}
                >
                    <Column
                        title='Name'
                        dataIndex='name'
                        key='name'
                        render={(text, record) => (
                            <a onClick={() => navigate(`/admin/specification-attribute-groups/${record.id}`)}>{text}</a>
                        )}
                    />
                    <Column align={'center'} width='20%' title='Display order' dataIndex='displayOrder' key='displayOrder' />
                    <Column
                        width='10%'
                        title='View'
                        key='view'
                        align={'center'}
                        render={(text, record) => (
                            <Button type='primary' onClick={() => showModal(record)}>
                                View
                            </Button>
                        )}
                    />
                </Table>
            </div>
            <Modal
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
                <Table dataSource={modalData} rowKey='id' pagination={false} rowSelection={rowSelection}>
                    <Column title='Name' dataIndex='name' key='name' />
                    <Column title='Display order' dataIndex='displayOrder' key='displayOrder' />
                    <Column
                        width='20%'
                        title='Edit'
                        key='edit'
                        render={(text, record) => (
                            <Link to={`/admin/specification-attributes/${record?.id}/update`}>
                                <Button type='primary' icon={<EditOutlined />} size='middle' onClick={() => handleEdit(record)}>
                                    Edit
                                </Button>
                            </Link>
                        )}
                    />
                </Table>
                <div style={{ marginTop: '16px', textAlign: 'center' }}>
                    <Pagination
                        current={current}
                        onChange={onChange}
                        total={ungroupedAttributesData.totalPages * ungroupedAttributesData.size}
                    />
                </div>
            </Modal>
        </div>
    )
}

export default SpecificationAttributeGroupManage
