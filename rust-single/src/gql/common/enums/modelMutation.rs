#[derive(Debug, Clone, Copy, Eq, PartialEq)]
pub enum ModelMutation {
  CREATED,
  UPDATED,
  DELETED,
}
