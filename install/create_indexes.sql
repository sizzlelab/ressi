create index cei_action on cos_events (action);
create index cei_semantic_event_id on cos_events (semantic_event_id);
create index cei_user_id on cos_events (user_id);
create index cei_application_id on cos_events (application_id);
create index cei_cos_session_id on cos_events (cos_session_id);
create index cei_created_at on cos_events (created_at);
create index cei_return_value on cos_events (return_value);

create index sei_action on service_events (action);
create index sei_semantic_event_id on service_events (semantic_event_id);
create index sei_user_id on service_events (user_id);
create index sei_application_id on service_events (application_id);
create index sei_session_id on service_events (session_id);
create index sei_created_at on service_events (created_at);
create index sei_return_value on service_events (return_value);

