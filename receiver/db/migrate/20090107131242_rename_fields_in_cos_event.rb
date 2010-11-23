class RenameFieldsInCosEvent < ActiveRecord::Migration
  def self.up
    rename_column :cos_events, :userid, :user_id
    rename_column :cos_events, :applicationid, :application_id
    rename_column :cos_events, :sessionid, :cos_session_id
    rename_column :cos_events, :address, :ip_address

    remove_column :cos_events, :method
    remove_column :cos_events, :headers

    add_column :cos_events, :return_value, :string
    add_column :cos_events, :http_user_agent, :string
    add_column :cos_events, :request_uri, :string
    add_column :cos_events, :http_referer, :string
  end

  def self.down
    rename_column :cos_events, :user_id, :userid
    rename_column :cos_events, :application_id, :applicationid
    rename_column :cos_events, :cos_session_id, :sessionid
    rename_column :cos_events, :ip_address, :address

    add_column :cos_events, :method, :string
    add_column :cos_events, :headers, :string

    remove_column :cos_events, :return_value
    remove_column :cos_events, :http_user_agent
    remove_column :cos_events, :request_uri
    remove_column :cos_events, :http_referer
  end
end
