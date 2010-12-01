# This file is auto-generated from the current state of the database. Instead of editing this file, 
# please use the migrations feature of Active Record to incrementally modify your database, and
# then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your database schema. If you need
# to create the application database on another system, you should be using db:schema:load, not running
# all the migrations from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 20101201140231) do

  create_table "cos_events", :force => true do |t|
    t.string   "user_id"
    t.string   "application_id"
    t.string   "cos_session_id"
    t.string   "ip_address"
    t.string   "action"
    t.text     "parameters"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "return_value"
    t.string   "http_user_agent"
    t.string   "request_uri"
    t.string   "http_referer"
    t.string   "semantic_event_id"
  end

  create_table "service_events", :force => true do |t|
    t.string   "user_id"
    t.string   "application_id"
    t.string   "session_id"
    t.string   "ip_address"
    t.string   "action"
    t.text     "parameters"
    t.string   "return_value"
    t.string   "http_user_agent"
    t.string   "request_uri"
    t.string   "http_referer"
    t.string   "semantic_event_id"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "test_group_number"
  end

end
