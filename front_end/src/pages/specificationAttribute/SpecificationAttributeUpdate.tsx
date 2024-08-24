import { useState, useEffect } from 'react'
import {
    Form,
    Input,
    Select,
    Button,
    Row,
    Col,
    Table,
    Space,
    Modal,
    Checkbox,
    message,
    InputNumber,
    ColorPicker,
} from 'antd'
import { useNavigate, useParams } from 'react-router-dom'
import { DeleteOutlined, EditOutlined, ExclamationCircleOutlined } from '@ant-design/icons'
import SpecificationAttributeConfigs from '@/pages/specificationAttribute/SpecificationAttributeConfigs.ts'
import useGetByIdApi from '@/hooks/use-get-by-id-api.ts'
import { SpecificationAttributeResponse } from '@/model/SpecificationAttribute.ts'
import SpecificationAttributeGroupConfigs from '@/pages/specificationAttributeGroup/SpecificationAttributeGroupConfigs.ts'
import useUpdateApi from '@/hooks/use-update-api.ts'
import useDeleteByIdsApi from '@/hooks/use-delete-by-ids-api.ts'
import { CheckboxChangeEvent } from 'antd/es/checkbox'
import {
    SpecificationAttributeGroupNameResponse,
    SpecificationAttributeGroupResponse,
} from '@/model/SpecificationAttributeGroup'
import Title from 'antd/es/typography/Title'
import { AggregationColor } from 'antd/es/color-picker/color'
import useGetApi from '@/hooks/use-get-api'

const { Option } = Select
const { confirm } = Modal

interface Option {
    id: number
    specificationAttribute: number
    name: string
    displayOrder: number
    color: string | null
    associatedProducts: number
}
interface FormValues {
    name: string
    group: string
    displayOrder: number
}

interface OptionsListProps {
    options: Option[]
    onDelete: (id: number) => void
    onEdit: (record: Option) => void
}

function OptionsList({ options = [], onDelete, onEdit }: OptionsListProps) {
    const columns = [
        { title: 'Name', dataIndex: 'name', key: 'name' },
        { title: 'Display order', dataIndex: 'displayOrder', key: 'displayOrder' },
        { title: 'Number of associated products', dataIndex: 'associatedProducts', key: 'associatedProducts' },
        {
            title: 'Color',
            dataIndex: 'color',
            key: 'color',
            render: (color: string) => (
                <div
                    style={{
                        width: '20px',
                        height: '20px',
                        backgroundColor: color,
                        border: '1px solid #ccc',
                        borderRadius: '4px',
                    }}
                />
            ),
        },
        {
            title: 'Actions',
            key: 'actions',
            width: '20%',
            render: (record: Option) => (
                <Space size='middle'>
                    <Button type='primary' icon={<EditOutlined />} onClick={() => onEdit(record)}>
                        Edit
                    </Button>
                    <Button danger icon={<DeleteOutlined />} onClick={() => onDelete(record.id)}>
                        Delete
                    </Button>
                </Space>
            ),
        },
    ]

    return <Table columns={columns} dataSource={options} pagination={{ pageSize: 6 }} />
}

function SpecificationAttributeUpdate() {
    const [form] = Form.useForm()
    const [modalForm] = Form.useForm()
    const { id } = useParams()
    const [editData, setEditData] = useState<SpecificationAttributeResponse | null>(null)
    const [setGroups] = useState<SpecificationAttributeGroupResponse[]>([])
    const [isModalVisible, setIsModalVisible] = useState(false)
    const [isColorPickerVisible, setIsColorPickerVisible] = useState(false)
    const [color, setColor] = useState('')
    const [options, setOptions] = useState<Option[]>([])
    const [editingOption, setEditingOption] = useState<Option | null>(null)
    const navigate = useNavigate()
    function rgbToHex(r: number, g: number, b: number): string {
        return (
            '#' +
            [r, g, b]
                .map((x) => {
                    const hex = x.toString(16)
                    return hex.length === 1 ? '0' + hex : hex
                })
                .join('')
                .toUpperCase()
        )
    }

    const formatColorHex = (hex: string) => {
        // Ensure the color is a valid 6-character hex code
        if (!/^#[0-9A-Fa-f]{6}$/.test(hex)) {
            return '#FFFFFF' // Default to white if the color is not valid
        }
        return hex.toUpperCase()
    }

    const { data, isLoading: isLoadingAttribute } = useGetByIdApi<SpecificationAttributeResponse>(
        SpecificationAttributeConfigs.resourceUrl,
        SpecificationAttributeConfigs.resourceKey,
        Number(id),
    )

    const {
        data: groupData,
        isLoading: isLoadingGroups,
        error,
    } = useGetApi<SpecificationAttributeGroupNameResponse[]>(
        `${SpecificationAttributeGroupConfigs.resourceUrl}/list-name`,
        SpecificationAttributeGroupConfigs.resourceKey,
    )

    const { mutate: updateAttribute } = useUpdateApi<SpecificationAttributeResponse, SpecificationAttributeResponse>(
        SpecificationAttributeConfigs.resourceUrl,
        SpecificationAttributeConfigs.resourceKey,
        Number(id),
    )

    const { mutate: deleteOptions } = useDeleteByIdsApi<number>(SpecificationAttributeConfigs.resourceUrl, 'options')
    const { mutate: deleteAttribute } = useDeleteByIdsApi<number>(
        SpecificationAttributeConfigs.resourceUrl,
        'attributes',
    )

    useEffect(() => {
        if (data) {
            setEditData(data)
            form.setFieldsValue({
                name: data.name,
                group: data.specificationAttributeGroupId || 'none',
                displayOrder: data.displayOrder,
            })

            if (data.listOptions) {
                const formattedOptions = data.listOptions.map((option) => ({
                    id: option.id,
                    specificationAttribute: option.specificationAttribute,
                    name: option.name || '',
                    displayOrder: option.displayOrder || 0,
                    associatedProducts: 0,
                    color: option.colorSquaresRgb !== null ? option.colorSquaresRgb : null, // Ensure proper mapping
                }))
                setOptions(formattedOptions)
            }
        }
    }, [data, form])

    useEffect(() => {
        if (groupData && groupData.items) {
            setGroups(groupData.items)
        }
    }, [groupData, setGroups])

    if (isLoadingAttribute || isLoadingGroups) {
        return <div>Loading...</div>
    }

    if (error) {
        return <div>Error loading data: {error.message}</div>
    }

    // For example, check the data before setting the value
    const processColor = (color: string | null): string | null => {
        if (color === null || color === undefined || color === '') {
            return null // Returns null if color is null, undefined, or an empty string
        }
        return color // Returns the color value unchanged if not null or an empty string
    }

    const showEditModal = (option: Option) => {
        console.log('Option Color:', option.color) // Debugging value

        setEditingOption(option)
        setIsModalVisible(true)
        modalForm.setFieldsValue({
            optionName: option.name,
            optionDisplayOrder: option.displayOrder,
        })

        // Update the state of color and checkbox
        const processedColor = processColor(option.color)
        if (processedColor !== null) {
            setColor(processedColor)
            setIsColorPickerVisible(true) //Checkbox is selected if colored
        } else {
            setColor('#FFFFFF') // Or default color value
            setIsColorPickerVisible(false) // Checkbox is not selected if there is no color
        }
    }

    const handleCancel = () => {
        setIsModalVisible(false)
        setIsColorPickerVisible(false)
        setColor('#FFFFFF')
        modalForm.resetFields()
        setEditingOption(null)
    }

    const handleOk = () => {
        const newOption = modalForm.getFieldsValue() as {
            optionName: string
            optionDisplayOrder: number
        }

        // Determine the final color to be saved
        let finalColor: string | null = null
        if (isColorPickerVisible) {
            finalColor = color === '#FFFFFF' ? '#FFFFFF' : color
        } else {
            finalColor = null
        }

        console.log('Final Color:', finalColor)
        console.log('New Option Details:', {
            name: newOption.optionName || '',
            displayOrder: Number(newOption.optionDisplayOrder) || 0,
            color: finalColor,
        })

        if (editingOption) {
            setOptions((prevOptions) =>
                prevOptions.map((option) =>
                    option.id === editingOption.id
                        ? {
                              ...option,
                              name: newOption.optionName || '',
                              displayOrder: Number(newOption.optionDisplayOrder) || 0,
                              color: finalColor,
                          }
                        : option,
                ),
            )
        } else {
            setOptions((prevOptions) => [
                ...prevOptions,
                {
                    id: prevOptions.length + 1,
                    specificationAttribute: Number(id),
                    name: newOption.optionName || '',
                    displayOrder: Number(newOption.optionDisplayOrder) || 0,
                    associatedProducts: 0,
                    color: finalColor,
                },
            ])
        }
        handleCancel()
    }

    const handleColorChange = (value: AggregationColor) => {
        const { r, g, b } = value.toRgb() // Assuming value has a `toRgb` method returning an object with r, g, b properties
        const hex = rgbToHex(r, g, b)
        setColor(hex)
    }

    const handleColorCheckChange = (e: CheckboxChangeEvent) => {
        const isChecked = e.target.checked
        setIsColorPickerVisible(isChecked)

        // If the checkbox is not selected, set the color to null or default value
        if (!isChecked) {
            setColor('#FFFFFF') // Or another default value if needed
        }
    }

    const handleSave = () => {
        const formValues = form.getFieldsValue() as FormValues

        const updatedOptions = options.map((option) => ({
            id: Number(option.id),
            name: option.name,
            colorSquaresRgb: option.color === '#FFFFFF' ? '#FFFFFF' : option.color,
            displayOrder: option.displayOrder,
            productSpecificationAttributeMappings: [],
            specificationAttributeId: Number(id),
        }))

        const dataToSend = {
            id: Number(id),
            name: formValues.name,
            displayOrder: Number(formValues.displayOrder),
            specificationAttributeGroupId: formValues.group === 'none' ? null : Number(formValues.group),
            listOptions: updatedOptions,
        }

        // Show confirmation dialog
        Modal.confirm({
            title: 'Are you sure you want to save?',
            content: 'Your changes will be saved, and you will be redirected.',
            okText: 'Yes',
            cancelText: 'No',
            onOk: () => {
                updateAttribute(dataToSend, {
                    onSuccess: () => {
                        message.success('Save and continue edit successfully')
                        navigate('/admin/specification-attribute-groups')
                    },
                    onError: () => {
                        message.error('Error save and continue edit failed')
                    },
                })
            },
            onCancel: () => {
                console.log('Save operation cancelled')
            },
        })
    }

    const handleSaveAndContinue = () => {
        const formValues = form.getFieldsValue() as FormValues

        const updatedOptions = options.map((option) => ({
            id: Number(option.id),
            name: option.name,
            colorSquaresRgb: option.color === '#FFFFFF' ? '#FFFFFF' : option.color,
            displayOrder: option.displayOrder,
            productSpecificationAttributeMappings: [],
            specificationAttributeId: Number(id),
        }))

        const dataToSend = {
            id: Number(id),
            name: formValues.name,
            displayOrder: Number(formValues.displayOrder),
            specificationAttributeGroupId: formValues.group === 'none' ? null : Number(formValues.group),
            listOptions: updatedOptions,
        }

        updateAttribute(dataToSend, {
            onSuccess: () => {
                message.success('Save and continue edit successfully')
            },
            onError: () => {
                message.error('Error save and continue edit failed')
            },
        })
    }

    const handleDeleteOption = (optionId: number) => {
        confirm({
            title: 'Are you sure you want to delete this option?',
            icon: <ExclamationCircleOutlined />,
            onOk() {
                deleteOptions([optionId], {
                    onSuccess: () => {
                        setOptions((prevOptions) => prevOptions.filter((option) => option.id !== optionId))
                    },
                    onError: (error) => {
                        console.error('Error deleting option:', error)
                    },
                })
            },
            onCancel() {
                console.log('Cancel delete operation')
            },
        })
    }

    const handleDeleteAttribute = () => {
        confirm({
            title: 'Are you sure you want to delete this attribute?',
            icon: <ExclamationCircleOutlined />,
            content: 'This action cannot be undone.',
            onOk() {
                deleteAttribute([Number(id)], {
                    onSuccess: () => {
                        navigate('/admin/specification-attribute-groups')
                    },
                    onError: (error) => {
                        console.error('Error deleting attribute:', error)
                    },
                })
            },
            onCancel() {
                console.log('Cancel delete operation')
            },
        })
    }

    return (
        <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
            <Row justify='space-between' align='middle' style={{ marginBottom: '20px' }}>
                <Col style={{ textAlign: 'center', marginBottom: '20px' }}>
                    <Title
                        level={4}
                        style={{
                            margin: 0,
                            color: 'green',
                            fontWeight: '500',
                            fontSize: '18px',
                            lineHeight: '1.5',
                        }}
                    >
                        Edit specification attribute details - {editData?.name || 'New Attribute'}
                    </Title>
                </Col>
                <Col>
                    <Button type='primary' style={{ marginRight: '10px' }} onClick={handleSave}>
                        Save
                    </Button>
                    <Button type='default' style={{ marginRight: '10px' }} onClick={handleSaveAndContinue}>
                        Save and Continue Edit
                    </Button>
                    <Button danger icon={<DeleteOutlined />} onClick={handleDeleteAttribute}>
                        Delete
                    </Button>
                </Col>
            </Row>
            <Form initialValues={{ displayOrder: 0 }} form={form} layout='vertical' style={{ marginBottom: '20px' }}>
                <Form.Item
                    name='name'
                    label='Name'
                    tooltip='Set the name'
                    rules={[
                        { required: true, message: 'Please input the group name!' },
                        { max: 100, message: 'Name cannot exceed 100 characters!' },
                    ]}
                >
                    <Input style={{ maxWidth: 700 }} maxLength={101} />
                </Form.Item>
                <Form.Item
                    style={{ maxWidth: 350 }}
                    name='group'
                    label='Group'
                    rules={[{ required: true, message: 'Please select a group!' }]}
                >
                    <Select placeholder='Select a group'>
                        <Option value='none'>None</Option>
                        {groupData?.map((group) => (
                            <Option key={group.id} value={group.id}>
                                {group.name}
                            </Option>
                        ))}
                    </Select>
                </Form.Item>

                <Form.Item
                    label='Display order'
                    name='displayOrder'
                    tooltip='Set the display order'
                    rules={[
                        { required: true, message: 'Please enter the display order!' },
                        {
                            type: 'number',
                            min: 0,
                            max: 2000000,
                            message: 'Display order must be between 0 and 2,000,000!',
                        },
                    ]}
                >
                    <InputNumber type='number' />
                </Form.Item>
            </Form>
            <h2>Options</h2>
            <OptionsList options={options} onDelete={handleDeleteOption} onEdit={showEditModal} />
            <Button type='dashed' style={{ marginTop: '20px' }} onClick={() => setIsModalVisible(true)}>
                Add a new option
            </Button>

            <Modal
                title={editingOption ? 'Edit Option' : 'Add New Option'}
                open={isModalVisible}
                onOk={handleOk}
                onCancel={handleCancel}
                okText={editingOption ? 'Update' : 'Add'}
                cancelText='Cancel'
            >
                <Form initialValues={{ displayOrder: 0 }} form={modalForm} layout='vertical'>
                    <Form.Item
                        name='optionName'
                        label='Name'
                        tooltip='Set the name'
                        rules={[
                            { required: true, message: 'Please input the option name!' },
                            { max: 100, message: 'Name cannot exceed 100 characters!' },
                        ]}
                    >
                        <Input maxLength={101} />
                    </Form.Item>
                    <Form.Item label='Specify color'>
                        <Checkbox onChange={handleColorCheckChange} checked={isColorPickerVisible}>
                            Enable color picker
                        </Checkbox>
                        {isColorPickerVisible && (
                            <div style={{ marginTop: '10px' }}>
                                <ColorPicker value={color} onChange={handleColorChange} />
                                <div style={{ marginTop: '10px' }}>
                                    <strong>Selected Color:</strong> {formatColorHex(color)}
                                </div>
                            </div>
                        )}
                    </Form.Item>
                    <Form.Item
                        label='Display order'
                        name='optionDisplayOrder'
                        tooltip='Set the display order'
                        rules={[
                            { required: true, message: 'Please enter the display order!' },
                            {
                                type: 'number',
                                min: 0,
                                max: 2000000,
                                message: 'Display order must be between 0 and 2,000,000!',
                            },
                        ]}
                    >
                        <InputNumber type='number' />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    )
}

export default SpecificationAttributeUpdate
